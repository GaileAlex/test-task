package ee.gaile.tuum.controller;

import ee.gaile.tuum.model.request.TransactionRequest;
import ee.gaile.tuum.model.response.TransactionHistoryResponse;
import ee.gaile.tuum.model.response.TransactionResponse;
import ee.gaile.tuum.service.TransactionService;
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

import java.util.List;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Tag(name = "TransactionController", description = "Controller for managing transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Service for performing transactions")
    public ResponseEntity<TransactionResponse> makeTransaction(@Valid @RequestBody TransactionRequest request) {
        return new ResponseEntity<>(transactionService.makeTransaction(request), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Service for obtaining a list of account transactions")
    public ResponseEntity<List<TransactionHistoryResponse>> getAccountTransactions(@PathVariable Long accountId) {
        return new ResponseEntity<>(transactionService.getAccountTransactions(accountId), HttpStatus.OK);
    }

}
