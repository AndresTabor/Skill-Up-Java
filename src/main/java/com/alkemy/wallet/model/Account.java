package com.alkemy.wallet.model;

import com.alkemy.wallet.model.enums.Currency;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{currency.notnull}")
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull(message = "{transactionlimit.notnull}")
    @Column(name = "transaction_limit", nullable = false)
    private Double transactionLimit;

    @NotNull(message = "{balance.notnull}")
    @Column(name = "balance")
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;

    @NotNull(message = "{softdelete.notnull}")
    @Column(name = "soft_delete", nullable = false)
    private boolean softDelete;

    public Account(Currency currency) {
        this.balance = 0.;
        if (currency == Currency.ARS) {
            this.transactionLimit = 300000.0;
        } else {
            this.transactionLimit = 1000.;
        }
        this.currency = currency;
    }
}
