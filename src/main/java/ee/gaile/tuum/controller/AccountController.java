package ee.gaile.tuum.controller;

import ee.gaile.tuum.model.request.AccountRequest;
import ee.gaile.tuum.model.response.Account;
import ee.gaile.tuum.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Tag(name = "AccountController", description = "Controller for account management")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Service for creating an account")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequest request) {
        return new ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Service for obtaining account information")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        return new ResponseEntity<>(accountService.getAccount(accountId), HttpStatus.OK);
    }

}
