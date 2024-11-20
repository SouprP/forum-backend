package me.souprpk.forumbackend.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntyPoint jwtAuthEntyPoint;

    private CustomUserDetailService userDetailsService;

    public SecurityConfig(CustomUserDetailService userDetailsService, JwtAuthEntyPoint jwtAuthEntyPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthEntyPoint = jwtAuthEntyPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(jwtAuthEntyPoint));
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/api/auth/**").permitAll()
                        //.anyRequest().authenticated())
                        .anyRequest().permitAll());
        http.httpBasic(withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}
