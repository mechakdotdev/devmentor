package com.devmentor.service;

import com.devmentor.entity.User;
import com.devmentor.exception.ResourceNotFoundException;
import com.devmentor.repository.UserRepository;
import com.devmentor.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    public List<User> getUsers(List<String> usernames) {
        return userRepository.findAllByUsernameIn(usernames);
    }
}