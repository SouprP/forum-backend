package me.souprpk.forumbackend.api.dto.responses;

import lombok.Data;

@Data
public class AuthTokenResponse {
    private String token;
    private String tokenType = "Bearer";

    public AuthTokenResponse(String token) {
        this.token = token;
    }
}
