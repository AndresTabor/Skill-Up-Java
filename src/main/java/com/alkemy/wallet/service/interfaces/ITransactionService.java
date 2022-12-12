package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.dto.RequestTransactionDto;
import com.alkemy.wallet.dto.ResponseTransactionDto;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

public interface ITransactionService {
    HashSet<ResponseTransactionDto> getByUserId(@Valid List<Account> accounts);

    ResponseTransactionDto createTransactions(Transaction transactionIncome, Transaction transactionPayment);

    ResponseEntity<Object> makeTransaction(String token, RequestTransactionDto destinedTransactionDto);

    Page<ResponseTransactionDto> findAllTransactionsByUserIdPageable(Long id, int page, String token);

    ResponseEntity<?> getTransaction(Long id, String token);

    ResponseEntity<?>  patchTransaction(Long id, String token, String description);

    boolean checkBalance(Double balance, Double amount);

    boolean checkTransactionAmount(Double amount);

    ResponseEntity<?> createPayment(RequestTransactionDto transctionDto);

    ResponseEntity<?> createDeposit(RequestTransactionDto transactionDto);
}
