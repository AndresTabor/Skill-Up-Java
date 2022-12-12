package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.exception.ResourceNotFoundException;
import com.alkemy.wallet.exception.UserNotLoggedException;
import com.alkemy.wallet.mapper.Mapper;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.interfaces.IUserService;
import com.alkemy.wallet.util.JwtUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final Mapper mapper;
    private final JwtUtil jwtUtil;

    private final MessageSource messageSource;

    public UserService(IUserRepository userRepository,
                       Mapper mapper,
                       JwtUtil jwtUtil,
                       MessageSource messageSource) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
        this.messageSource = messageSource;
    }

    @Override
    public boolean checkLoggedUser(String token) {
        if (jwtUtil.getValue(token) != null)
            return true;
        throw new UserNotLoggedException(messageSource.getMessage(
                "user.notlogged.exception",
                null,
                Locale.ENGLISH));
    }

    @Override
    public User findLoggedUser(String token) {
        User user = userRepository.findByEmail(jwtUtil.getValue(token));
        if (user != null) {
            return user;
        }
        throw new UserNotLoggedException(messageSource.getMessage(
                "user.notlogged.exception",
                null,
                Locale.ENGLISH));
    }

    @Override
    public UserDto findByEmail(String email) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findOptionalByEmail(email);
        if (user.isPresent()) {
            return mapper.getMapper().map(user.get(), UserDto.class);
        }
        throw new ResourceNotFoundException(
                messageSource.getMessage(
                        "email.notfound.exception",
                        new Object[]{email},
                        Locale.ENGLISH));
    }

}
