package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.dto.RequestTransactionDto;
import com.alkemy.wallet.dto.ResponseTransactionDto;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Hidden
public interface ITransactionService {
    HashSet<ResponseTransactionDto> getByUserId(@Valid List<Account> accounts);

    ResponseTransactionDto createTransactions(Transaction transactionIncome, Transaction transactionPayment);

    ResponseEntity<Object> makeTransaction(RequestTransactionDto destinedTransactionDto);

    Page<ResponseTransactionDto> findAllTransactionsByUserIdPageable(Long id, int page);

    ResponseEntity<?> getTransaction(Long id);

    ResponseEntity<?>  patchTransaction(Long id, String description);

    boolean checkBalance(Double balance, Double amount);

    boolean checkTransactionAmount(Double amount);

    ResponseEntity<?> createPayment(RequestTransactionDto transctionDto);

    ResponseEntity<?> createDeposit(RequestTransactionDto transactionDto);
}
