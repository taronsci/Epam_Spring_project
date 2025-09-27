package com.booky.demo.config;

//import com.booky.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Since I was running my frontend via live server in Visual Studio Code,
 * I was on a different port and cookies were not saving. I was getting a cookie when I logged in,
 * but it wasn't saving.
 *
 * I had no time, so I decided to leave it as is. Otherwise, I was planning on using Principal.
 */
@Configuration
public class SecurityConfig {

//    private final UserService userService;
//
//    public SecurityConfig(UserService userService){
//        this.userService = userService;
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(withDefaults())
                .authorizeHttpRequests(auth-> auth.anyRequest().permitAll());
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/user/login",
//                                "/api/user/signup",
//                                "/api/listing",
//                                "/api/*"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/user/login")
//                        .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
//                        .failureHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/api/user/logout")
//                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
//                        .invalidateHttpSession(true)      // invalidate session on server
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//                .userDetailsService(userService);

        return http.build();
    }

}
