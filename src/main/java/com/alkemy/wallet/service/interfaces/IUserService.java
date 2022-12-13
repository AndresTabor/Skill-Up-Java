package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.dto.AuthToken;
import com.alkemy.wallet.dto.LoginUserDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;

@Hidden
public interface IUserService {

    boolean checkLoggedUser(String token);

    User findLoggedUser(String token);

    ResponseEntity<?> softDelete(String token, Long id);

    ResponseEntity<?> login(LoginUserDto loginUser);
}
