package com.marcol.homeworkApp.controller;

import com.marcol.homeworkApp.model.Details;
import com.marcol.homeworkApp.model.User;
import com.marcol.homeworkApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    User user;
    Details details;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setup(){
        details = new Details();
        details.setShoeSize(39);
        details.setHomeTown("Katowice");

        user = new User();
        user.setId(1L);
        user.setName("TestName");
        user.setSurname("TestSurname");

        user.setDetails(details);
        details.setUser(user);
    }

    @Test
    void shouldCreateUser() {
        when(userService.createUser(any(User.class))).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        assertEquals(user.getName(), responseEntity.getBody().getName());
    }

    @Test
    void shouldReturnUser() {
        when(userService.getUser(1L)).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.getUser(user.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getId(), responseEntity.getBody().getId());
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        when(userService.getUser(2L)).thenReturn(null);
        ResponseEntity<User> responseEntity = userController.getUser(2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void shouldReturnListOfUsers() {
        Details detailsTwo = new Details();
        detailsTwo.setShoeSize(39);
        detailsTwo.setHomeTown("Katowice");

        User userTwo = new User();
        userTwo.setId(2L);
        userTwo.setName("TestName");
        userTwo.setSurname("TestSurname");

        userTwo.setDetails(detailsTwo);
        detailsTwo.setUser(userTwo);

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(userTwo);

        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void shouldUpdateUser() {
        User userUpdated = new User();
        userUpdated.setId(1L);
        user.setName("TestNameUpdated");
        user.setSurname("TestSurnameUpdated");

        when(userService.updateUser(userUpdated)).thenReturn(userUpdated);
        ResponseEntity<User> responseEntity = userController.updateUser(userUpdated);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userUpdated.getSurname(), responseEntity.getBody().getSurname());
    }

    @Test
    void shouldReturnNotFoundWhenUserToUpdateWasNotFound() {
        User userUpdated = new User();
        userUpdated.setId(1L);
        user.setName("TestNameUpdated");
        user.setSurname("TestSurnameUpdated");

        when(userService.updateUser(userUpdated)).thenReturn(null);
        ResponseEntity<User> responseEntity = userController.updateUser(userUpdated);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void shouldDeleteUser() {
        ResponseEntity<String> responseEntity = userController.deleteUser(user.getId());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userService, times(1)).deleteUser(argumentCaptor.capture());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getId(), argumentCaptor.getValue());
    }
}