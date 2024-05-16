package ee.gaile.tuum.service.impl;

import ee.gaile.tuum.apiexeption.ApiException;
import ee.gaile.tuum.model.enums.CurrencyEnum;
import ee.gaile.tuum.model.request.TransactionRequest;
import ee.gaile.tuum.model.response.Account;
import ee.gaile.tuum.model.response.BalanceResponse;
import ee.gaile.tuum.model.response.TransactionHistoryResponse;
import ee.gaile.tuum.model.response.TransactionResponse;
import ee.gaile.tuum.repository.AccountRepository;
import ee.gaile.tuum.repository.BalanceRepository;
import ee.gaile.tuum.repository.TransactionRepository;
import ee.gaile.tuum.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ee.gaile.tuum.model.enums.TransactionDirectionEnum.IN;
import static ee.gaile.tuum.model.enums.TransactionDirectionEnum.OUT;

/**
 * @author Aleksei Gaile 20-Apr-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RabbitTemplate rabbitTemplate;
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    @Value(value = "${rabbit.queue.transaction}")
    private String queueName;

    public List<TransactionHistoryResponse> getAccountTransactions(Long accountId){
        return transactionRepository.getAccountTransactions(accountId);
    }

    @Override
    @Retryable(maxAttempts = 15, noRetryFor = {ApiException.class})
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TransactionResponse makeTransaction(TransactionRequest request) {
        Account account = accountRepository.getAccountById(request.getAccountId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Account not found"));

        Map<CurrencyEnum, BigDecimal> balancesMap = account.getBalances().stream()
                .collect(Collectors.toMap(BalanceResponse::getCurrencyType, BalanceResponse::getBalance));

        checkAmount(request.getAmount());

        BigDecimal balance = calculateBalance(request, balancesMap.get(request.getCurrency()));

        balanceRepository.updateAccountBalance(request.getAccountId(), request.getCurrency(), balance);

        TransactionResponse response = createResponse(account, request, balance);

        rabbitTemplate.convertAndSend("", queueName, response);

        return response;
    }

    private void checkAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }
    }

    private TransactionResponse createResponse(Account account, TransactionRequest request, BigDecimal balance) {
        TransactionResponse response = new TransactionResponse()
                .setAccountId(account.getAccountId())
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
                .setDirection(request.getTransactionDirection())
                .setDescription(request.getDescription())
                .setBalance(balance);

        Long transactionId = transactionRepository.insertTransaction(response);
        response.setTransactionId(transactionId);

        return response;
    }

    private BigDecimal calculateBalance(TransactionRequest request, BigDecimal balance) {
        if (request.getTransactionDirection().equals(IN)) {
            balance = balance.add(request.getAmount());
        } else if (request.getTransactionDirection().equals(OUT)) {
            if (balance.compareTo(request.getAmount()) < 0) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Not enough money");
            }
            balance = balance.subtract(request.getAmount());
        }

        return balance;
    }

}
