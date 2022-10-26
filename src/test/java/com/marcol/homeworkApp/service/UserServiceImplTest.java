package com.marcol.homeworkApp.service;

import com.marcol.homeworkApp.model.Details;
import com.marcol.homeworkApp.model.User;
import com.marcol.homeworkApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void shouldCreateUser() {
        //given
        User user = createUserForTest();
        when(userRepository.save(any(User.class))).thenReturn(user);
        //when
        User userCreated = userService.createUser(user);
        //then
        assertThat(userCreated.getName(), equalTo(user.getName()));
    }

    @Test
    void shouldReturnUser() {
        //given
        User user = createUserForTest();
        user.setId(1L);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        //when
        User userRetrieved = userService.getUser(user.getId());
        //then
        assertThat(userRetrieved.getName(), equalTo(user.getName()));
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        //given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        //when
        User userRetrieved = userService.getUser(any(Long.class));
        //then
        assertNull(userRetrieved);
    }

    @Test
    void shouldReturnListOfUsers() {
        //given
        User userOne = createUserForTest();
        User userTwo = createUserForTest();
        List<User> users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);
        when(userRepository.findAll()).thenReturn(users);
        //when
        List<User> usersRetrieved = userService.getAllUsers();
        //then
        assertThat(usersRetrieved.size(), equalTo(2));
    }

    @Test
    void shouldUpdateUser() {
        //given
        User userToUpdate = createUserForTest();
        Details details = createDetails(userToUpdate);
        userToUpdate.setDetails(details);
        userToUpdate.setId(1L);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);
        //when
        User userUpdated = userService.updateUser(userToUpdate);
        //then
        assertNotNull(userUpdated);
    }

    @Test
    void shouldNotUpdateUserWhenUserNotFound() {
        //given
        User userToUpdate = createUserForTest();
        Details details = createDetails(userToUpdate);
        userToUpdate.setDetails(details);
        userToUpdate.setId(1L);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        //when
        User userUpdated = userService.updateUser(userToUpdate);
        //then
        assertNull(userUpdated);
    }

    @Test
    void shouldDeleteUser() {
        //given
        User user = createUserForTest();
        user.setId(1L);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        //when
        userService.deleteUser(user.getId());
        //then
        verify(userRepository, times(1)).deleteById(argumentCaptor.capture());
        assertThat(user.getId(), equalTo(argumentCaptor.getValue()));
    }

    private User createUserForTest(){
        User user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        return user;
    }

    private Details createDetails(User user){
        Details details = new Details();
        details.setHomeTown("TestTown");
        details.setShoeSize(40);
        details.setUser(user);
        return details;
    }
}