package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController<User, UserService>{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return this.userService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id,
                                               @RequestBody User user) {

        logger.info("updating " + user.toString());
        User updatedUser = this.getService().updateUser(id, user);

        if (updatedUser != null) {
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
