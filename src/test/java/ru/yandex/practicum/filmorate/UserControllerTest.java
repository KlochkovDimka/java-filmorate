package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"test1\", " +
                "\"name\":\"test1\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yandex@mail.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("2008-10-12"));
    }

    @Test
    public void createEmptyEmailUserTest() throws Exception {
        String jsonString = "{\"email\":\"\", " +
                "\"login\":\"login\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createWrongEmailUserTest() throws Exception {
        String jsonString = "{\"email\":\"emailEmail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createEmptyLoginUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createWrongLoginUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"  \", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createEmptyNameUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"test2\", " +
                "\"name\":\"\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test2"));
    }

    @Test
    public void createBirthdayAfterUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"\", " +
                "\"birthday\":\"3000-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void putUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"test3\", " +
                "\"name\":\"test3\", " +
                "\"birthday\":\"2008-10-12\"} ";

        String jsonStringUpdate = "{\"id\":1," +
                "\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"test3\", " +
                "\"name\":\"test3\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringUpdate))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void putFriend() throws Exception {
        createUsers();
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/users/1/friends/2"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void getUser() throws Exception {
        createUsers();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yandex@mail.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("2008-10-12"));
    }

    @Test
    public void deleteFriendByUser() throws Exception {
        createUsers();
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/users/1/friends/2"))
                .andExpect(MockMvcResultMatchers.status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/users/1/friends/2"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    private void createUsers() throws Exception {
        String user = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        String userTwo = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"userTwo\", " +
                "\"name\":\"UserTwo\", " +
                "\"birthday\":\"2008-10-12\"} ";

        String friend = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"loginFriend\", " +
                "\"name\":\"nameFriend\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(friend));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userTwo));
    }
}
