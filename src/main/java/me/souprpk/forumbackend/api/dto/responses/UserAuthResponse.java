package me.souprpk.forumbackend.api.dto.responses;

import lombok.Data;

@Data
public class UserAuthResponse {
    private String username;
    private boolean hasAuth;
}
