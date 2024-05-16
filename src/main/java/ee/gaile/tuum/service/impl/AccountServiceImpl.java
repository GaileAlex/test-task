package ee.gaile.tuum.service.impl;

import ee.gaile.tuum.apiexeption.ApiException;
import ee.gaile.tuum.model.request.AccountRequest;
import ee.gaile.tuum.model.response.Account;
import ee.gaile.tuum.repository.AccountRepository;
import ee.gaile.tuum.repository.BalanceRepository;
import ee.gaile.tuum.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aleksei Gaile 18-Apr-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Override
    @Transactional
    public Account createAccount(AccountRequest request) {
        Long accountId = accountRepository.insertAccount(request);
        request.getCurrencyTypes().forEach(type -> balanceRepository.insertCurrencyType(accountId, type));
        log.info("An account with id {} has been created.", accountId);

        return getAccount(accountId);
    }

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.getAccountById(accountId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Account not found"));
    }

}
