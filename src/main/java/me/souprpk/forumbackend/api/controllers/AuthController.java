package me.souprpk.forumbackend.api.controllers;

import me.souprpk.forumbackend.api.dto.requests.LoginRequest;
import me.souprpk.forumbackend.api.dto.requests.RegisterRequest;
import me.souprpk.forumbackend.api.dto.responses.AuthTokenResponse;
import me.souprpk.forumbackend.api.dto.responses.UserAuthResponse;
import me.souprpk.forumbackend.api.models.Role;
import me.souprpk.forumbackend.api.models.UserEntity;
import me.souprpk.forumbackend.api.repository.RoleRepository;
import me.souprpk.forumbackend.api.repository.UserRepository;
import me.souprpk.forumbackend.api.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody LoginRequest loginRequest){
        // check if user exists
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // generating a new JWT token for the user
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthTokenResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterRequest registerRequest) {
        // check if user already exists
        if(userRepository.existsByUsername(registerRequest.getUsername()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // create a new user object
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode((registerRequest.getPassword())));

        // give the user a role
        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        // insert the user into the database
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        // get the info about the user who the GET request
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser;

        // check if the user exists (is logged in)
        try{
            currentUser = (User) authentication.getPrincipal();
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    // REDO THIS FOR MORE SECURITY AS IT'S A BIG SECURITY RISK (don't use the goddam username)
    @PostMapping("/has-auth/{username}")
    public ResponseEntity<UserAuthResponse> getUserAuth(@PathVariable String username) {
        // user sending the request
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser;

        UserAuthResponse response = new UserAuthResponse();

        try{
            currentUser = (User) authentication.getPrincipal();
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        response.setUsername(currentUser.getUsername());

        // checks if the user sending the requests is the same as in the provided username
        if(username.equals(currentUser.getUsername())){
            response.setHasAuth(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // checks if the user sending the request is an ADMIN
        for(var authority : currentUser.getAuthorities())
            if(authority.getAuthority().equals("ADMIN")){
                response.setHasAuth(true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        response.setHasAuth(false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
