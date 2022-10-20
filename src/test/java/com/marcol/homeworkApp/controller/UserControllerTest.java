package com.marcol.homeworkApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcol.homeworkApp.model.User;
import com.marcol.homeworkApp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldCreateUserWhenPostInvoked() throws Exception {
        //given
        User user = createUserForTest();
        when(userService.createUser(any(User.class))).thenReturn(user);
        String userToCreateInJSON = objectMapper.writeValueAsString(user);
        String expectedResponseBody = "{\"name\":\"TestName\"}";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                        .post("/user")
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .content(userToCreateInJSON)
                                                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.CREATED.value()));
        assertEquals(expectedResponseBody, mockHttpServletResponse.getContentAsString(), false);
    }

    @Test
    void shouldReturnUserWhenGetInvoked() throws Exception {
        //given
        when(userService.getUser(any(Long.class))).thenReturn(createUserForTest());
        String expectedResponseBody = "{\"name\":\"TestName\"}";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                        .get("/user/1/")
                                                        .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform((requestBuilder)).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.OK.value()));
        assertEquals(expectedResponseBody, mockHttpServletResponse.getContentAsString(), false);
    }

    @Test
    void shouldNotReturnUserWhenGetInvokedAndUserMissing() throws Exception {
        //given
        when(userService.getUser(any(Long.class))).thenReturn(null);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                        .get("/user/1/")
                                                        .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform((requestBuilder)).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void shouldReturnListOfUsersWhenGetAllInvoked() throws Exception {
        //given
        User userOne = createUserForTest();
        userOne.setName("TestName1");
        User userTwo = createUserForTest();
        userTwo.setName("TestName2");
        List<User> users = new ArrayList<>(Arrays.asList(userOne, userTwo));
        when(userService.getAllUsers()).thenReturn(users);
        String expectedResponseBody = "[{\"name\":\"TestName1\"}, {\"name\":\"TestName2\"}]";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                        .get("/users")
                                                        .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform((requestBuilder)).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.OK.value()));
        assertEquals(expectedResponseBody, mockHttpServletResponse.getContentAsString(), false);
    }

    @Test
    void shouldUpdateUserWhenPutInvoked() throws Exception {
        //given
        User user = createUserForTest();
        user.setName("TestNameUpdated");
        when(userService.updateUser(any(User.class))).thenReturn(user);
        String userToUpdateInJSON = objectMapper.writeValueAsString(user);
        String expectedResponseBody = "{\"name\":\"TestNameUpdated\"}";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                    .put("/user")
                                                    .accept(MediaType.APPLICATION_JSON)
                                                    .content(userToUpdateInJSON)
                                                    .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.OK.value()));
        assertEquals(expectedResponseBody, mockHttpServletResponse.getContentAsString(), false);
    }

    @Test
    void shouldNotUpdateUserWhenPutInvokedButUserNotFound() throws Exception {
        //given
        User user = createUserForTest();
        user.setName("TestNameUpdated");
        when(userService.updateUser(any(User.class))).thenReturn(null);
        String userToUpdateInJSON = objectMapper.writeValueAsString(user);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                        .put("/user")
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .content(userToUpdateInJSON)
                                                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void shouldDeleteUserWhenDeleteInvoked() throws Exception {
        //given
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                                    .delete("/user/1/")
                                                    .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform((requestBuilder)).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        //then
        verify(userService, times(1)).deleteUser(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo(1L));
        assertThat(mockHttpServletResponse.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(mockHttpServletResponse.getContentAsString(),
                    equalTo(String.format(UserController.DELETE_MESSAGE, 1)));
    }

    private User createUserForTest(){
        User user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        return user;
    }
}