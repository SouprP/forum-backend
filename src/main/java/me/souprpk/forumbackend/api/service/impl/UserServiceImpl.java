package me.souprpk.forumbackend.api.service.impl;

import me.souprpk.forumbackend.api.models.UserEntity;
import me.souprpk.forumbackend.api.repository.UserRepository;
import me.souprpk.forumbackend.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
