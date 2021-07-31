package com.example.cinemaspringapp.services;

import com.example.cinemaspringapp.dto.UserDTO;
import com.example.cinemaspringapp.exception.UserException;
import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.cinemaspringapp.messages.Messages.*;
import static com.example.cinemaspringapp.model.Role.ROLE_ADMIN;
import static com.example.cinemaspringapp.model.Role.ROLE_USER;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void register(UserDTO userDTO) throws UserException {

        if (userRepository.findUserByEmail(userDTO.getEmail())
                .isPresent()) {
            throw new UserException(
                    String.format(USER_ALREADY_EXISTS_MSG, userDTO.getEmail())
            );
        }

        User newUser = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .role(ROLE_USER)
                .build();


        userRepository.save(newUser);


    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }
}
