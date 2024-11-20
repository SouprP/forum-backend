package me.souprpk.forumbackend.api.controllers;

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

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtGenerator.generateToken(authentication);
//        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<UserEntity> register(@RequestBody RegisterDto registerDto) {
//        if(userRepository.existsByUsername(registerDto.getUsername()))
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        UserEntity user = new UserEntity();
//        user.setEmail(registerDto.getEmail());
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
//
//        Role roles = roleRepository.findByName("USER").get();
//        user.setRoles(Collections.singletonList(roles));
//
//        userRepository.save(user);
//
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<User> getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser;
//
//        try{
//            currentUser = (User) authentication.getPrincipal();
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(currentUser, HttpStatus.OK);
//    }
//
//    @PostMapping("/has-auth/{username}")
//    public ResponseEntity<HasAuthDto> getUserAuth(@PathVariable String username) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser;
//
//        HasAuthDto response = new HasAuthDto();
//
//        try{
//            currentUser = (User) authentication.getPrincipal();
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//
//        response.setUsername(currentUser.getUsername());
//
//        if(username.equals(currentUser.getUsername())){
//            response.setHasAuth(true);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//
//
//        for(var authority : currentUser.getAuthorities())
//            if(authority.getAuthority().equals("ADMIN")){
//                response.setHasAuth(true);
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//
//        response.setHasAuth(false);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
