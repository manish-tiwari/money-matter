package com.poc.moneymatter.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.dao.repository.UserRepository;
import com.poc.moneymatter.services.UserService;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan({"com.poc.moneymatter"})
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private User user;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {

        if (this.mockMvc == null) {
            this.mockMvc = webAppContextSetup(webApplicationContext).build();
        }

        user = new User(UUID.randomUUID(), "User 1", 49, "user.email@poc.com", "Male", true);
        User user2 = new User(UUID.randomUUID(), "User 2", 55, "user2.email@poc.com", "Female", true);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(java.util.Optional.ofNullable(user));

        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(user);

        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Mockito.when(userRepository.findAll())
                .thenReturn(Arrays.asList(user, user2));
    }

    @Test
    public void testSaveUser() throws Exception {

        MvcResult result = mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user))
        ).andReturn();

        Assert.assertEquals(201, result.getResponse().getStatus());
        Assert.assertEquals(user, mapper.readValue(result.getResponse().getContentAsString(), User.class));
    }

    @Test
    public void testFindUserById() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/user/id/"+user.getId())
        ).andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertEquals(user, mapper.readValue(result.getResponse().getContentAsString(), User.class));
    }

    @Test
    public void testFindUserByEmailId() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/user/email/user.email@poc.com")
        ).andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertEquals(user, mapper.readValue(result.getResponse().getContentAsString(), User.class));
    }

    @Test
    public void testFindAllUsers() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/users")
        ).andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        List<User> users = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });
        Assert.assertEquals(2, users.size());
        Assert.assertEquals(user, users.get(0));
    }

    @Test
    public void testUpdateUser() throws Exception {

        user.setEmail("updatedEmail@poc.com");
        MvcResult result = mockMvc.perform(
                put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user))
        ).andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        final User actual = mapper.readValue(result.getResponse().getContentAsString(), User.class);
        Assert.assertEquals("updatedEmail@poc.com", actual.getEmail());
    }

    @Test
    @Ignore(value = "Ignore this test as we will add common error handler soon")
    public void testUpdateForInvalidUser() throws Exception {


        user.setId(null);
        thrown.expect(org.springframework.web.util.NestedServletException.class);
        thrown.expectMessage("User doesn't exist !");
        mockMvc.perform(
                put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user))
        );

    }

    @Test
    public void testDeleteUser() throws Exception {

        MvcResult result = mockMvc.perform(
                delete("/api/user/" + user.getId())
        ).andReturn();

        Assert.assertEquals(204, result.getResponse().getStatus());

        MvcResult findAll = mockMvc.perform(
                get("/api/user/user.email@poc.com")
        ).andReturn();

        Assert.assertEquals("", result.getResponse().getContentAsString());
    }
}
