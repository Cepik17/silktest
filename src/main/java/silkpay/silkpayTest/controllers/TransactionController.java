package silkpay.silkpayTest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silkpay.silkpayTest.dtos.MoneyTransfer;
import silkpay.silkpayTest.dtos.TransactionView;
import silkpay.silkpayTest.services.TransactionService;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionView moneyTransfer(@RequestBody MoneyTransfer moneyTransfer) {
        return transactionService.performTransfer(moneyTransfer);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
