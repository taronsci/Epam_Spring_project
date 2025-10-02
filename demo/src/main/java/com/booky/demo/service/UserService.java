package com.booky.demo.service;

import com.booky.demo.dao.UserDAO;
import com.booky.demo.dao.UserRepository;
import com.booky.demo.dto.UserDTO;
import com.booky.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserDAO userDAO;
    private UserRepository userRepository;

    public UserService(UserDAO userDAO,UserRepository userRepository) {
        this.userDAO = userDAO;
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public UserDTO findUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return new UserDTO(user.getId(), user.getUsername(),user.getEmail());
    }

    @Transactional
    public Optional<Integer> register(User user) {
        Optional<Integer> existingId = userDAO.getIdByUsername(user.getUsername());

        if(existingId.isPresent())
            return Optional.empty();

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return Optional.of(userDAO.register(user));
    }

    @Transactional
    public Optional<Integer> login(User user) {
        Optional<Integer> idOpt = userDAO.getIdByUsername(user.getUsername());

        if(idOpt.isEmpty())
            return Optional.empty();

        Integer id = idOpt.get();
        String storedHash = userDAO.getPasswordHashById(id);
        if (!passwordEncoder.matches(user.getPassword(), storedHash))
            return Optional.empty();

        return Optional.of(id);
    }



    @Transactional
    public ResponseEntity<UserDTO> updateProfile(User user, String name) {
        try {
            user.setId(userDAO.getIdByUsername(name).get());

            UserDTO updatedUser = userDAO.updateProfileDetails(user);

            UserDetails updatedDetails = loadUserByUsername(updatedUser.username());
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(updatedDetails, updatedDetails.getPassword(), updatedDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return ResponseEntity.ok(updatedUser);       //200 OK
        }catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)         //409 Conflict
                    .body(null);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();    //404 Not Found
        }
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
