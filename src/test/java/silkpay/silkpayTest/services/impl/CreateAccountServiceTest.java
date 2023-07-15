package silkpay.silkpayTest.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import silkpay.silkpayTest.dtos.AccountCreate;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.services.AccountService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreateAccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void createNewAccount() {
        AccountCreate accountCreate = AccountCreate.builder()
                .userId(10L)
                .initialBalance(BigDecimal.valueOf(100))
                .build();
        AccountView accountView = accountService.createNewAccount(accountCreate);
        assertNotNull(accountView.getUserId());
        assertNotNull(accountView.getCurrentBalance());
        assertEquals(accountCreate.getUserId(), accountView.getUserId());
    }
}