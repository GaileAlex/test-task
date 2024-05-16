package ee.gaile.tuum.service;

import ee.gaile.tuum.model.request.AccountRequest;
import ee.gaile.tuum.model.response.Account;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
public interface AccountService {

    Account createAccount(AccountRequest request);

    Account getAccount(Long accountId);

}
