package com.alkemy.wallet.service.interfaces;

import com.alkemy.wallet.model.User;

public interface IUserService {

    boolean checkLoggedUser(String token);

    User findLoggedUser(String token);

    void softDelete(String token, Long id);

}
