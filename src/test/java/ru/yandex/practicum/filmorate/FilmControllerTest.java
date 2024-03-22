package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createFilmTest() throws Exception {
        String jsonString = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"film\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"releaseDate\": \"2008-10-12\",\n" +
                "    \"duration\": 120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value("2008-10-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(120));
    }

    @Test
    public void createEmptyNameFilmTest() throws Exception {
        String jsonString = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"releaseDate\": \"2008-10-12\",\n" +
                "    \"duration\": 120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createDuration200CharFilmTest() throws Exception {
        String jsonString = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"film\",\n" +
                "    \"description\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Quisque laoreet interdum odio, vel imperdiet neque lobortis sed. Fusce consequat " +
                "bibendum erat, in eleifend dolor posuere sed. Nulla facilisi..\",\n" +
                "    \"releaseDate\": \"2008-10-12\",\n" +
                "    \"duration\": 120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void createDuration199CharFilmTest() throws Exception {
        String jsonString = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"film\",\n" +
                "    \"description\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Quisque laoreet interdum odio, vel imperdiet neque lobortis sed. Fusce consequat " +
                "bibendum erat, in eleifend dolor posuere sed. Nulla facilisi.\",\n" +
                "    \"releaseDate\": \"2008-10-12\",\n" +
                "    \"duration\": 120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void createFilmWithWrongReleaseDataTest() throws Exception {
        String jsonString = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"film\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"releaseDate\": \"1867-10-12\",\n" +
                "    \"duration\": 120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createFilmWithMinusDurationTest() throws Exception {
        String jsonString = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"film\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"releaseDate\": \"2008-10-12\",\n" +
                "    \"duration\": -120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void putLikeFilm() throws Exception {

        String user = "{\n" +
                "    \"likesFromUsers\": [],\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"film\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"releaseDate\": \"2008-10-12\",\n" +
                "    \"duration\": 120,\n" +
                "    \"mpa\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"PG-13\"\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user));

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/films/1/like/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteLikeFilm() throws Exception {

        String user = "{\"email\":\"yandex@mail.ru\", " +
                "\"login\":\"login\", " +
                "\"name\":\"name\", " +
                "\"birthday\":\"2008-10-12\"} ";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user));

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/films/1/like/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
