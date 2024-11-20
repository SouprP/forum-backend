package me.souprpk.forumbackend.api.service;

import me.souprpk.forumbackend.api.models.UserEntity;

public interface UserService {
    public UserEntity getUserByUsername(String username);
}
