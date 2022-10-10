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
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json", path = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id) {
        System.out.println("Hello from getUser method!");
        User userRetrieved = userService.getUser(id);
        if(userRetrieved != null){
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = "application/json", path = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("Hello from getAllUsers method!");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        System.out.println("Hello from updateUser method!");
        User userUpdated = userService.updateUser(user);
        if(userUpdated != null){
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(produces = "application/json", path = "user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id){
        System.out.println("Hello from deleteUser method!");
        userService.deleteUser(id);
        return new ResponseEntity<>("User has been deleted.", HttpStatus.OK);
    }
}
