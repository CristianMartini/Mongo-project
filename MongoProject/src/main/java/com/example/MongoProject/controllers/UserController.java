package com.example.MongoProject.controllers;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MongoProject.model.UserModel;
import com.example.MongoProject.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        try {
            UserModel createdUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel user, HttpServletResponse response) {
        Optional<UserModel> userFromDb = userRepository.findByName(user.getName());
        if (!userFromDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        } else {
            if (userFromDb.get().getPassword().equals(user.getPassword())) {
                Cookie cookie = new Cookie("meuCookie", "valorDoCookie");
                cookie.setPath("/");
                cookie.setMaxAge(3600);
                response.addCookie(cookie);

                return ResponseEntity.ok("Login realizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }
    }
}
