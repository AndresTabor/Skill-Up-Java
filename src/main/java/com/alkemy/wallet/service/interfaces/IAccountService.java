package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.dto.*;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.model.enums.Currency;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Hidden
public interface IAccountService {

    BasicAccountDto createAccount(AccountCreateDto accountCreateDto, User user);

    List<Account> getAccountsByUserId(Long userId);

    @Transactional(readOnly = true)
    Page<AccountDto> findAllAccountsPageable(int page) throws EmptyResultDataAccessException;

    @Transactional(readOnly = true)
    List<AccountDto> getAccountsByUserEmail(String email) throws EmptyResultDataAccessException;

    Account getAccountByCurrency(Long user_id, Currency currency);

    boolean checkAccountLimit(Account senderAccount, RequestTransactionDto transactionDto);

    ResponseEntity<?> updateAccount(Long id, AccountUpdateDto newTransactionLimit);

    boolean checkAccountExistence(Long user_id, Currency currency);

    ResponseEntity<?> postAccount(BasicAccountDto basicAccountDto);

    List<BalanceDto> getBalance();

    AccountDto updateBalance(Long id, Double amount);
}
