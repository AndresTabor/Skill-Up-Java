package com.alkemy.wallet.service;


import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.RequestTransactionDto;
import com.alkemy.wallet.dto.ResponseTransactionDto;
import com.alkemy.wallet.exception.*;
import com.alkemy.wallet.mapper.Mapper;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.model.enums.TypeOfTransaction;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.interfaces.IAccountService;
import com.alkemy.wallet.service.interfaces.ITransactionService;
import com.alkemy.wallet.service.interfaces.IUserService;
import com.alkemy.wallet.util.JwtUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.hibernate.internal.CoreLogging.logger;

@Hidden
@Service
public class TransactionService implements ITransactionService {

    @Autowired
    Mapper mapper;

    @Autowired
    ITransactionRepository transactionRepository;

    @Autowired
    IUserService userService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IAccountService accountService;

    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MessageSource messageSource;

    @Override
    public HashSet<ResponseTransactionDto> getByUserId(@Valid List<Account> accounts) {

        List<Long> accounts_id = new ArrayList<>();
        for (Account account : accounts) {
            accounts_id.add(account.getId());
        }

        return transactionRepository.findByAccount_idIn(accounts_id).stream().map((transaction) ->
                        mapper.getMapper().map(transaction, ResponseTransactionDto.class))
                .collect(Collectors.toCollection(HashSet::new));

    }

    @Override

    public ResponseTransactionDto createTransactions(Transaction transactionIncome, Transaction transactionPayment) {
        transactionRepository.save(transactionIncome);
        return mapper.getMapper().map(transactionRepository.save(transactionPayment), ResponseTransactionDto.class);
    }

    @Override
    public ResponseEntity<Object> makeTransaction(RequestTransactionDto destinedTransactionDto) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User senderUser = userRepository.findByEmail(auth.getName());
            Account senderAccount = accountService.getAccountByCurrency(senderUser.getId(),
                    destinedTransactionDto.getAccount().getCurrency());
            Account destinedAccount = accountRepository.findById(destinedTransactionDto.getAccount()
                    .getId()).orElseThrow(() -> new ResourceNotFoundException(messageSource
                    .getMessage("account.notfound.receiving.exception",
                            new Object[]{destinedTransactionDto.getAccount().getId()}, Locale.ENGLISH)));
            accountService.checkAccountLimit(senderAccount, destinedTransactionDto);
            checkBalance(senderAccount.getBalance(), destinedTransactionDto.getAmount());
            ResponseTransactionDto transactionPayment = createTransactions(new Transaction(destinedTransactionDto.getAmount(), TypeOfTransaction.income, destinedTransactionDto.getDescription(), destinedAccount),
                    new Transaction(destinedTransactionDto.getAmount(), TypeOfTransaction.payment, destinedTransactionDto.getDescription(), mapper.getMapper().map(senderAccount, Account.class)));

            senderAccount.setBalance(senderAccount.getBalance() - transactionPayment.getAmount());
            destinedAccount.setBalance(senderAccount.getBalance() + transactionPayment.getAmount());
            accountRepository.save(senderAccount);
            accountRepository.save(destinedAccount);
            //vuelvo a setear el sender account en la transaccion con el nuevo balance de la cuenta
            transactionPayment.setAccount(mapper.getMapper().map(senderAccount, AccountDto.class));
            return ResponseEntity.status(HttpStatus.OK).body(transactionPayment);
        } catch (ResourceNotFoundException | UserNotLoggedException | AccountLimitException |
                NotEnoughCashException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @Override
    public Page<ResponseTransactionDto> findAllTransactionsByUserIdPageable(Long id, int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Pageable pageable = PageRequest.of(page, 10);

        Page<ResponseTransactionDto> pageTransactions = transactionRepository.findByAccount_User_Id(id, pageable).map((transaction) ->
                mapper.getMapper().map(transaction, ResponseTransactionDto.class));

        return pageTransactions;

    }

    public ResponseEntity<?> getTransaction(Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (userRepository.findByEmail(auth.getName()) == (transactionRepository.findById(id)).get().getAccount().getUser()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapper.getMapper()
                            .map(transactionRepository.findById(id), ResponseTransactionDto.class));
            } throw new UserNotLoggedException(messageSource.getMessage(
                    "user.notlogged.exception",
                    new Object[]{id},
                    Locale.ENGLISH));
        } catch (UserNotLoggedException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
    }

    @Override
    public ResponseEntity<?> patchTransaction(Long id, String description) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (userRepository.findByEmail(auth.getName()) == (transactionRepository.findById(id)).get().getAccount().getUser()) {
                Transaction transaction = transactionRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(messageSource.getMessage(
                                "transaction.notfound.exception",
                                new Object[]{id},
                                Locale.ENGLISH)));
                transaction.setDescription(description);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(mapper.getMapper()
                                .map(transactionRepository.save(transaction), ResponseTransactionDto.class));
            } throw new UserNotLoggedException(messageSource.getMessage(
                    "user.notlogged.exception",
                    new Object[]{id},
                    Locale.ENGLISH));
        } catch (UserNotLoggedException e) {
            logger(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
    }

    @Override
    public boolean checkBalance(Double balance, Double amount) {
        if (balance < amount) {
            throw new NotEnoughCashException(messageSource
                    .getMessage("notenoughcash.exception", null, Locale.ENGLISH));
        }
        return true;
    }

    @Override
    public boolean checkTransactionAmount(Double amount) {
        if (amount <= 0) {
            throw new NoAmountException(messageSource
                    .getMessage("amount.exception", null, Locale.ENGLISH));
        }
        return true;
    }

    @Override
    public ResponseEntity<?> createPayment(RequestTransactionDto transactionDto) {
        try {
            Account account = accountRepository.findById(transactionDto.getAccount().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageSource.getMessage("account.notfound.exception",
                                    new Object[]{transactionDto.getAccount().getId()},
                                    Locale.ENGLISH)));
            checkTransactionAmount(transactionDto.getAmount());
            account.setBalance(account.getBalance() - transactionDto.getAmount());
            Transaction transaction = transactionRepository.save(new Transaction(
                    transactionDto.getAmount(),
                    TypeOfTransaction.payment,
                    transactionDto.getDescription(),
                    account));
            accountRepository.save(account);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.getMapper().map(transaction, ResponseTransactionDto.class));
        } catch (NoAmountException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
    }

    @Override
    public ResponseEntity<?> createDeposit(RequestTransactionDto transactionDto) {
        try {
            Account account = accountRepository.findById(transactionDto.getAccount().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageSource.getMessage(
                                    "account.notfound.exception",
                                    new Object[]{transactionDto.getAccount().getId()},
                                    Locale.ENGLISH)));
            checkTransactionAmount(transactionDto.getAmount());
            account.setBalance(account.getBalance() + transactionDto.getAmount());
            Transaction transaction = transactionRepository.save(new Transaction(transactionDto.getAmount(), TypeOfTransaction.deposit, transactionDto.getDescription(), account));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.getMapper().map(transaction, ResponseTransactionDto.class));
        } catch (NoAmountException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
    }

}