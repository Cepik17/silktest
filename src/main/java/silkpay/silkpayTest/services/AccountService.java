package silkpay.silkpayTest.services;

import silkpay.silkpayTest.dtos.AccountCreate;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.models.Account;

public interface AccountService {

    AccountView createNewAccount(AccountCreate accountCreate);

    AccountView getAccountBalance(Long accountId);

    Account getAccountById(Long accountId);
}
