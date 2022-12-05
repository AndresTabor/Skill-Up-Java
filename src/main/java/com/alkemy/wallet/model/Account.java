package com.alkemy.wallet.model;

import com.alkemy.wallet.model.enums.Currency;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "account")
@Data
@ApiModel("Cuenta")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @Getter
    @Setter
    @Column(name = "transaction_limit", nullable = false)
    private Double transactionLimit;

    @NotNull
    @Getter
    @Setter
    @Column(name = "balance")
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name="user_id", nullable = false)
//    private Long userId;

    @Column(name = "CREATION_DATE")
//    @CreationTimestamp
    private Date creationDate;

    @Column(name = "UPDATE_DATE")
//    @UpdateTimestamp
    private Date updateDate;

    @NotNull
    @Column(name = "soft_delete", nullable = false)
    private boolean softDelete;

    public Account() {
    }

    public Account(Currency currency) {
        this.balance = 0.;
//        this.user = user;
        if (currency == Currency.ars) {
            this.transactionLimit = 300000.0;
            this.currency = currency;
        }
        else {
            this.transactionLimit=1000.;
            this.currency=currency;
        }
    }
}
