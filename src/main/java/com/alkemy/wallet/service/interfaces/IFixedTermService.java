package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.dto.FixedTermDto;
import com.alkemy.wallet.dto.SimulatedFixedTermDto;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface IFixedTermService {

    FixedTermDto createFixedTerm(FixedTermDto fixedTermDto, String token);

    SimulatedFixedTermDto simulateFixedTerm(SimulatedFixedTermDto fixedTermDto);
}
