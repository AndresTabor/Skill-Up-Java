package com.alkemy.wallet.controller;

import com.alkemy.wallet.assembler.TransactionModelAssembler;
import com.alkemy.wallet.assembler.model.TransactionModel;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.mapper.Mapper;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.service.interfaces.IAccountService;
import com.alkemy.wallet.service.interfaces.ITransactionService;
import com.alkemy.wallet.service.interfaces.IUserService;
import com.alkemy.wallet.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
public class TransactionsController {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IAccountService accountService;

    private JwtUtil jwtUtil;

    private Mapper mapper;

    @Autowired
    private TransactionModelAssembler transactionModelAssembler;

    @Autowired
    private PagedResourcesAssembler<TransactionDto> pagedResourcesAssembler;

    @Autowired
    private IUserService userService;

    @GetMapping("/transactions/{userId}")
    @Operation(summary = "Get user's transactions",
            description = "Provides a list of the user's transactions",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "User´s id",
                    description = "Indicate the user's id in order to find his transactions"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Nothing found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = {@Content(mediaType = "application/json")})})
    public HashSet<TransactionDto> getTransactions(@PathVariable("userId") Long userId) {
        return transactionService.getByUserId(accountService.getAccountsByUserId(userId));
    }

    @GetMapping("/transaction/{id}")
    @Operation(summary = "Get transaction",
            description = "Provides an specific transaction",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "Transaction´s id",
                    description = "Indicate transaction id in order to find it"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Nothing found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<?> getTransaction(
            @PathVariable Long id,
            @Parameter(name = "Token",
                    required = true,
                    hidden = true)
            @RequestHeader(name = "Authorization") String token) {
        return transactionService.getTransaction(id, token);
    }

    @PatchMapping("/transactions/{id}")
    @Operation(summary = "Update a transaction",
            description = "Update an existent transaction",
            tags = "Transaction Controller",
            parameters = {@Parameter(name = "Transaction´s id",
                    description = "Indicate transaction's id in order to find it"),
                    @Parameter(name = "Transaction's description",
                            description = "Indicate transaction's description in order to update it")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Nothing found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<?> patchTransaction(
            @PathVariable("id") Long id,
            @Parameter(name = "Token",
                    required = true,
                    hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestBody String description) {
        return transactionService.patchTransaction(id, token, description);
    }

    @GetMapping("/transactions/page/{id}")
    @Operation(summary = "Get user's transactions",
            description = "Provides a paged list of the user's transactions",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "User´s id",
                    description = "Indicate user's id in order to find his transactions"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Nothing found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<PagedModel<TransactionModel>> getTransactionPage(
            @PathVariable("id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @Parameter(name = "Token",
                    required = true,
                    hidden = true)
            @RequestHeader("Authorization") String token) {
        Page<TransactionDto> transactions = transactionService.findAllTransactionsByUserIdPageable(userId, page, token);

        PagedModel<TransactionModel> model = pagedResourcesAssembler.toModel(transactions, transactionModelAssembler);

        return ResponseEntity.ok().body(model);
    }

    @PostMapping("/transactions/sendUsd")
    @Operation(summary = "Send USD",
            description = "Generates a transaction in USD from a logged user",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "TransactionDto",
                    description = "Transaction info and destined account"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction generated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Object> sendUsd(
            @Parameter(name = "Token",
                    required = true,
                    hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestBody TransactionDto destinedTransactionDto) {
        return transactionService.makeTransaction(token, destinedTransactionDto);
    }

    @PostMapping("/transactions/sendArs")
    @Operation(summary = "Send ARS",
            description = "Generates a transaction in ARS from a logged user",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "TransactionDto",
                    description = "Transaction info and destined account"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction generated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Object> sendArs(
            @Parameter(name = "Token",
                    required = true,
                    hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestBody TransactionDto destinedTransactionDto) {
        return transactionService.makeTransaction(token, destinedTransactionDto);
    }

    @PostMapping("/transactions/deposit")
    @Operation(summary = "Create deposit",
            description = "Generates a deposit",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "Deposit info",
                    description = "Indicate deposit info in order to execute it"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Deposit generated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Limit exceeded",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<?> postDeposit(@RequestBody TransactionDto transactionDto) {
        return transactionService.createDeposit(transactionDto);
    }

    @PostMapping("/transactions/payment")
    @Operation(summary = "Pay",
            description = "Generates a payment",
            tags = "Transaction Controller",
            parameters = @Parameter(name = "Payment info",
                    description = "Indicate payment info in order to execute it"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Payment generated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Limit exceeded",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<?> postPayment(@RequestBody TransactionDto transactionDto) {
        return transactionService.createPayment(transactionDto);
    }
}