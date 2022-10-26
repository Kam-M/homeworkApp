package com.marcol.homeworkApp.service;

import com.marcol.homeworkApp.model.User;
import com.marcol.homeworkApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    @Override
    public User updateUser(User user) {
        User userToUpdate = getUser(user.getId());
        if(userToUpdate != null){
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userToUpdate.getDetails().setHomeTown(user.getDetails().getHomeTown());
            userToUpdate.getDetails().setShoeSize(user.getDetails().getShoeSize());
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
