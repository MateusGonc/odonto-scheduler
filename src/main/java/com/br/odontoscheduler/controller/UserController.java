package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.service.PatientService;
import com.br.odontoscheduler.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user, @PathVariable String id) {
        Optional<User> opUser = userService.findById(id);

        if (opUser.isPresent()) {
            return ResponseEntity.ok(opUser.get());
        }

        return ResponseEntity.notFound().build();
    }
}
