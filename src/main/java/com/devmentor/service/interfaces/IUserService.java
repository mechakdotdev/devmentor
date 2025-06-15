package com.devmentor.service.interfaces;

import com.devmentor.entity.User;

public interface IUserService {
    User register(User user);
    User getByUsername(String username);
}