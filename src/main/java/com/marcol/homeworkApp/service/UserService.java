package com.marcol.homeworkApp.service;

import com.marcol.homeworkApp.model.User;

import java.util.List;

public interface UserService {

    public User createUser(User user);
    public User getUser(Long id);
    public User updateUser(User user);
    public void deleteUser(Long id);
    public List<User> getAllUsers();
}
