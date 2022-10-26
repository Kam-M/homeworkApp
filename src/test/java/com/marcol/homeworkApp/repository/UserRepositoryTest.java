package com.marcol.homeworkApp.repository;

import com.marcol.homeworkApp.model.User;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    static void setup() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082", "-ifNotExists")
                .start();
    }
    @Test
    void shouldSaveUserAndReturnUser(){
        //given
        User user = createUserForTest();
        //when
        User userSaved = userRepository.save(user);
        //then
        assertThat(userSaved, is(notNullValue()));
    }

    @Test
    void shouldReturnUserById(){
        //given
        User user = createUserForTest();
        userRepository.save(user);
        //when
        Optional<User> userRetrieved = userRepository.findById(user.getId());
        //then
        assertThat(userRetrieved, is(notNullValue()));
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
        assertThat(users.size(), equalTo(2));
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
        assertThat(userUpdated.getName(), equalTo(nameUpdated));
    }

    @Test
    void shouldDeleteUser(){
        //given
        User user = createUserForTest();
        User userSaved = userRepository.save(user);
        //when
        userRepository.deleteById(userSaved.getId());
        //then
        assertThat(userRepository.findAll().size(), equalTo(0));
    }

    private User createUserForTest(){
        User user = new User();
        user.setId(1L);
        user.setName("TestName");
        user.setSurname("TestSurname");
        return user;
    }
}