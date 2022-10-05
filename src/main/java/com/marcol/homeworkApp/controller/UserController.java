package com.marcol.homeworkApp.controller;

import com.marcol.homeworkApp.model.User;
import com.marcol.homeworkApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println("Hello from createUser method!");
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/{id}", produces = "application/json")
    public ResponseEntity<User> getEmployee(@PathVariable(value = "empId") Long id) {
        System.out.println("Hello from getUser method!");
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("Hello from getAllUsers method!");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
