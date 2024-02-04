package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void createUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yandex@mail.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("login"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("2008-10-12"));
    }

    @Test
    public void createEmptyEmailUserTest() throws Exception {
        String jsonString = "{\"email\":\"\", " +
                "\"login\":\"login\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(post("http://localhost:8080/users")
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

        mockMvc.perform(post("http://localhost:8080/users")
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

        mockMvc.perform(post("http://localhost:8080/users")
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

        mockMvc.perform(post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createEmptyNameUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("login"));
    }

    @Test
    public void createBirthdayAfterUserTest() throws Exception {
        String jsonString = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"\", " +
                "\"birthday\":\"3000-10-12\"} ";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void putUserTest() throws Exception {
        String jsonString = "{\"id\":1," +
                "\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"\", " +
                "\"birthday\":\"3000-10-12\"} ";

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}
