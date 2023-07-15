package silkpay.silkpayTest.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import silkpay.silkpayTest.dtos.MoneyTransfer;
import silkpay.silkpayTest.dtos.TransactionView;
import silkpay.silkpayTest.mappers.TransactionMapper;
import silkpay.silkpayTest.models.Account;
import silkpay.silkpayTest.models.Transaction;
import silkpay.silkpayTest.repositories.AccountRepository;
import silkpay.silkpayTest.repositories.TransactionRepository;
import silkpay.silkpayTest.services.AccountService;
import silkpay.silkpayTest.services.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionView performTransfer(MoneyTransfer moneyTransfer) {
        Transaction transaction = new Transaction();
        BigDecimal amountToTransfer = moneyTransfer.getAmount();
        Account accountFrom = accountService.getAccountById(moneyTransfer.getFromAccountId());
        Account accountTo = accountService.getAccountById(moneyTransfer.getToAccountId());
        if (accountFrom.getCurrentBalance().compareTo(amountToTransfer) < 0) {
            throw new IllegalArgumentException("Insufficient balance!");
        }
        if (amountToTransfer.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Amount must be bigger than 0");
        }
        accountTo.setCurrentBalance(accountTo.getCurrentBalance().add(amountToTransfer));
        accountFrom.setCurrentBalance(accountFrom.getCurrentBalance().subtract(amountToTransfer));
        transaction.setFromAccount(accountFrom);
        transaction.setToAccount(accountTo);
        transaction.setAmount(moneyTransfer.getAmount());
        transaction.setTransactionTime(LocalDateTime.now());
        transactionRepository.save(transaction);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        return transactionMapper.toView(transaction);
    }

}
