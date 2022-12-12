package com.alkemy.wallet.model;

import com.alkemy.wallet.model.enums.TypeOfTransaction;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "transactions")
//@ApiModel("Transacción")
public class Transaction {

    @Id
    @Column(name = "transaction_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{amount.notnull}")
    @Column(name = "amount")
    private Double amount;

    @NotNull(message = "{typeoftransaction.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeOfTransaction type;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "transaction_date")
    @CreationTimestamp
    private Date transactionDate;

    public Transaction(Double amount, TypeOfTransaction type, String description, Account account) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.account = account;
    }

}
