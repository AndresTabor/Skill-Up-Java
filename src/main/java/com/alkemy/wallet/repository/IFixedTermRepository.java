package com.alkemy.wallet.repository;

import com.alkemy.wallet.dto.FixedTermDto;
import com.alkemy.wallet.model.FixedTermDeposit;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Hidden
public interface IFixedTermRepository extends JpaRepository<FixedTermDeposit, Long> {

    List<FixedTermDeposit> findAllByAccount_Id(Long id);
}
