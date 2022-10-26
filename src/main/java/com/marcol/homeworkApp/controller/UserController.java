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

    public static final String METHOD_INVOKED = "%s method invoked.";
    public static final String DELETE_MESSAGE = "Delete operation has been performed. Requested id: %s";

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println(String.format(METHOD_INVOKED, "createUser"));

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json", path = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id) {
        System.out.println(String.format(METHOD_INVOKED, "getUser"));

        User userRetrieved = userService.getUser(id);
        if(userRetrieved != null){
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = "application/json", path = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println(String.format(METHOD_INVOKED, "getAllUsers"));

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        System.out.println(String.format(METHOD_INVOKED, "updateUser"));

        User userUpdated = userService.updateUser(user);
        if(userUpdated != null){
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(produces = "application/json", path = "user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id){
        System.out.println(String.format(METHOD_INVOKED, "deleteUser"));

        userService.deleteUser(id);
        return new ResponseEntity<>(String.format(DELETE_MESSAGE, id), HttpStatus.OK);
    }
}
