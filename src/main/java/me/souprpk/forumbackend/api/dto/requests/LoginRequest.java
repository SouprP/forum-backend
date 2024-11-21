package me.souprpk.forumbackend.api.dto.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
