package silkpay.silkpayTest.services;

import silkpay.silkpayTest.dtos.MoneyTransfer;
import silkpay.silkpayTest.dtos.TransactionView;

public interface TransactionService {
    TransactionView performTransfer(MoneyTransfer moneyTransfer);
}
