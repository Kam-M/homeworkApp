package com.marcol.homeworkApp.repository;

import com.marcol.homeworkApp.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveUserAndReturnUser(){
        //given
        User user = createUserForTest();
        //when
        User userSaved = userRepository.save(user);
        //then
        assertThat(userSaved).isNotNull();
    }

    @Test
    void shouldReturnUserById(){
        //given
        User user = createUserForTest();
        userRepository.save(user);
        //when
        Optional<User> userRetrieved = userRepository.findById(user.getId());
        //then
        assertThat(userRetrieved).isNotNull();
    }

    @Test
    void shouldReturnListOfUsers(){
        //given
        User userOne = createUserForTest();
        userOne.setId(1L);
        userRepository.saveAndFlush(userOne);
        User userTwo = createUserForTest();
        userTwo.setId(2L);
        userRepository.saveAndFlush(userTwo);
        //when
        List<User> users = userRepository.findAll();
        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void shouldUpdateUser(){
        //given
        String nameUpdated = "NameUpdated";
        User user = createUserForTest();
        user.setId(1L);
        userRepository.save(user);
        Optional<User> userRetrieved = userRepository.findById(user.getId());
        userRetrieved.get().setName(nameUpdated);
        //when
        User userUpdated = userRepository.save(userRetrieved.get());
        //then
        assertThat(userUpdated.getName()).isEqualTo(nameUpdated);
    }

    @Test
    void shouldDeleteUser(){
        //given
        User user = createUserForTest();
        User userSaved = userRepository.save(user);
        //when
        userRepository.deleteById(userSaved.getId());
        //then
        assertThat(userRepository.findAll().size()).isEqualTo(0);
    }

    private User createUserForTest(){
        User user = new User();
        user.setId(1L);
        user.setName("TestName");
        user.setSurname("TestSurname");
        return user;
    }
}