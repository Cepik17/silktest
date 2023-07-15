package silkpay.silkpayTest.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import silkpay.silkpayTest.dtos.AccountCreate;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.services.AccountService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAccountBalanceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void getAccountBalance() {
        AccountCreate accountCreate = new AccountCreate();
        accountCreate.setUserId(1L);
        accountCreate.setInitialBalance(BigDecimal.valueOf(100));

        AccountView createdAccount = accountService.createNewAccount(accountCreate);
        Long createdAccountId = createdAccount.getId();
        AccountView accountView = accountService.getAccountBalance(createdAccountId);

        assertNotNull(accountView, "Account is null");
        assertEquals(0, BigDecimal.valueOf(100).compareTo(accountView.getCurrentBalance()), "Balance does not match");
    }


}