package silkpay.silkpayTest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silkpay.silkpayTest.dtos.AccountCreate;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.services.AccountService;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public AccountView createNewAccount(@RequestBody AccountCreate accountCreate) {
        return accountService.createNewAccount(accountCreate);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @GetMapping("/{accountId}")
    public AccountView getAccountBalance(@PathVariable Long accountId) {
        return accountService.getAccountBalance(accountId);
    }


}
