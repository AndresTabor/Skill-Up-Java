package com.alkemy.wallet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@Entity
@Table(name = "fixed_deposits")
@ApiModel("Plazos fijos")
@AllArgsConstructor
@RequiredArgsConstructor
public class FixedTermDeposit {

    @Id
    @Column(name = "fixed_term_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{amount.notnull}")
    private Double amount;

    @NotNull(message = "{interest.notnull}")
    private Double interest;

    @NotNull(message = "{creationdate.notnull}")
    @CreationTimestamp
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate creationDate;

    @NotNull(message = "{closingdate.notnull}")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate closingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
