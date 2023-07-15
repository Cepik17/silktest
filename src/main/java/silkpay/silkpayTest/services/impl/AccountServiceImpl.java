package silkpay.silkpayTest.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import silkpay.silkpayTest.dtos.AccountCreate;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.mappers.AccountMapper;
import silkpay.silkpayTest.models.Account;
import silkpay.silkpayTest.repositories.AccountRepository;
import silkpay.silkpayTest.services.AccountService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountView createNewAccount(AccountCreate accountCreate) {
        if (accountRepository.getByUserId(accountCreate.getUserId()) == null) {
            Account newAcc = new Account();
            if (accountCreate.getInitialBalance().compareTo(BigDecimal.ZERO) >= 0) {
                newAcc.setInitialBalance(accountCreate.getInitialBalance());
                newAcc.setCurrentBalance(accountCreate.getInitialBalance());
            } else {
                newAcc.setInitialBalance(BigDecimal.ZERO);
                newAcc.setCurrentBalance(BigDecimal.ZERO);
            }
            newAcc.setUserId(accountCreate.getUserId());
            accountRepository.save(newAcc);
            return accountMapper.toView(newAcc);
        } else {
            throw new IllegalArgumentException("Account already exist");
        }
    }

    @Override
    public AccountView getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new IllegalArgumentException("No such account");
        } else return accountMapper.toView(account);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    }
}
