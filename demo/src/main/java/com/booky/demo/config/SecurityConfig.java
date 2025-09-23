package com.booky.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // For APIs (stateless)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults()); // or formLogin(withDefaults());


//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .oauth2ResourceServer(oauth2 ->
//                        oauth2.jwt(jwt -> {})   // <- supply custom JWT settings here if needed
//                );

        return http.build();
    }

}
