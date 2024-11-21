package me.souprpk.forumbackend.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private LocalDate date;
}
