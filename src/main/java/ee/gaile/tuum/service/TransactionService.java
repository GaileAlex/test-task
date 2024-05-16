package ee.gaile.tuum.service;

import ee.gaile.tuum.model.request.TransactionRequest;
import ee.gaile.tuum.model.response.TransactionHistoryResponse;
import ee.gaile.tuum.model.response.TransactionResponse;

import java.util.List;

/**
 * @author Aleksei Gaile 20-Apr-24
 */
public interface TransactionService {

    TransactionResponse makeTransaction(TransactionRequest request);

    List<TransactionHistoryResponse> getAccountTransactions(Long accountId);

}
