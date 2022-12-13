package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.exception.ResourceNotFoundException;
import com.alkemy.wallet.exception.UserNotAllowed;
import com.alkemy.wallet.exception.UserNotFound;
import com.alkemy.wallet.exception.UserNotLoggedException;
import com.alkemy.wallet.listing.RoleName;
import com.alkemy.wallet.mapper.Mapper;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.interfaces.IUserService;
import com.alkemy.wallet.util.JwtUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Hidden
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final Mapper mapper;

    private final JwtUtil jwtUtil;

    public UserService(IUserRepository userRepository, Mapper mapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean checkLoggedUser(String token) {
        if (jwtUtil.getValue(token) != null) {
            return true;
        }
        throw new UserNotLoggedException("User not logged");
    }

    @Override
    public User findLoggedUser(String token) {
        User user = userRepository.findByEmail(jwtUtil.getValue(token));
        if (user != null) {
            return user;
        }
        throw new UserNotLoggedException("User not logged");
    }

    @Override
    public UserDto findByEmail(String email) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findOptionalByEmail(email);
        if (user.isPresent()) {
            return mapper.getMapper().map(user.get(), UserDto.class);
        }
        throw new ResourceNotFoundException("Email not found");
    }

    @Override
    public ResponseEntity<?> softDelete(String token, Long id) {
        try {
            User loggedUser = findLoggedUser(token);
            User userToDelete = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            if (loggedUser.getRole().getName() == RoleName.ROLE_ADMIN) {
                userToDelete.setSoftDelete(Boolean.TRUE);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(userToDelete);
            } else if (loggedUser.getRole().getName() == RoleName.ROLE_USER && Objects.equals(userToDelete.getId(), loggedUser.getId())) {
                loggedUser.setSoftDelete(Boolean.TRUE);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(userToDelete);
            } else throw new UserNotAllowed("This action is forbidden for this user");

        } catch (ResourceNotFoundException | UserNotLoggedException | UserNotAllowed e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }

    }
}
