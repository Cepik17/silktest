package silkpay.silkpayTest.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import silkpay.silkpayTest.dtos.AccountCreate;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.dtos.MoneyTransfer;
import silkpay.silkpayTest.dtos.TransactionView;
import silkpay.silkpayTest.models.Account;
import silkpay.silkpayTest.repositories.AccountRepository;
import silkpay.silkpayTest.services.AccountService;
import silkpay.silkpayTest.services.TransactionService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyTransferTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        Account accountFrom = new Account();
        accountFrom.setInitialBalance(BigDecimal.valueOf(0));
        accountFrom.setCurrentBalance(BigDecimal.valueOf(100));
        accountFrom.setUserId(1L);

        Account accountTo = new Account();
        accountTo.setInitialBalance(BigDecimal.valueOf(0));
        accountTo.setCurrentBalance(BigDecimal.valueOf(100));
        accountTo.setUserId(2L);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }

    @Test
    void performTransaction() {
        MoneyTransfer moneyTransfer = new MoneyTransfer(1L, 2L, BigDecimal.valueOf(70));
        TransactionView transactionView = transactionService.performTransfer(moneyTransfer);

        assertNotNull(transactionView);
        assertEquals(moneyTransfer.getAmount(), transactionView.getAmount());

        AccountView updatedAccountFrom = accountService.getAccountBalance(1L);
        AccountView updatedAccountTo = accountService.getAccountBalance(2L);
        assertEquals(0, BigDecimal.valueOf(30).compareTo(updatedAccountFrom.getCurrentBalance()), "Balance does not match");
        assertEquals(0, BigDecimal.valueOf(170).compareTo(updatedAccountTo.getCurrentBalance()), "Balance does not match");
    }

    @Test
    public void performTransfer_InsufficientBalance() {
        MoneyTransfer moneyTransfer = new MoneyTransfer(1L, 2L, BigDecimal.valueOf(300));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.performTransfer(moneyTransfer);
        });

        String expectedMessage = "Insufficient balance!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void performTransfer_ZeroBalance() {
        AccountCreate accountFromCreate = new AccountCreate(3L, BigDecimal.valueOf(0));
        accountService.createNewAccount(accountFromCreate);

        MoneyTransfer moneyTransfer = new MoneyTransfer(3L, 2L, BigDecimal.valueOf(50));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.performTransfer(moneyTransfer);
        });

        String expectedMessage = "Insufficient balance!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void performTransfer_TransferZero() {
        MoneyTransfer moneyTransfer = new MoneyTransfer(1L, 2L, BigDecimal.valueOf(0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.performTransfer(moneyTransfer);
        });

        String expectedMessage = "Amount must be bigger than 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void performTransfer_AccountDoesNotExist() {
        MoneyTransfer moneyTransfer = new MoneyTransfer(1L, 100L, BigDecimal.valueOf(50));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.performTransfer(moneyTransfer);
        });

        String expectedMessage = "Account not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
