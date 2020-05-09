package com.alfonzjanfrithz.todoboard.todoboardapi.controller;

import com.alfonzjanfrithz.project.todoboard.model.ProjectResponse;
import com.alfonzjanfrithz.todoboard.todoboardapi.mapper.ProjectModelMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static helpers.ProjectRequestBuilder.project;
import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProjectController Integration Test")
@Log4j2
class ProjectControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        log.info("Deleting records from table");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "project");
    }

    @Test
    @DisplayName("Should Return OK Response when request sent to /projects endpoint")
    public void shouldReturnOkOnProjectsEndpoint() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return all available projects when request sent to /projects endpoint")
    public void shouldReturnAllRecordsOnProjectsEndpoint() throws Exception {
        createProject("project", 2);

        mockMvc.perform(
                get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        createOneProject();

        mockMvc.perform(
                get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    @DisplayName("Should be able to retrieve project by project id")
    public void shouldRetrieveExistingProject() throws Exception {
        ProjectResponse project = createOneProjectWithName("project-1");

        Long projectId = project.getId();
        mockMvc.perform(
                get("/projects/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("project-1")))
                .andExpect(jsonPath("$.id", is(projectId.intValue())));
    }

    @Test
    @DisplayName("Should be able to search project by name")
    public void shouldRetrieveExistingProjectByName() throws Exception {
        ProjectResponse projectOne = createOneProjectWithName("Project One");
        ProjectResponse projectTwo = createOneProjectWithName("Project Two");

        mockMvc.perform(
                get("/projects").param("name", "Project One"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(projectOne.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(projectOne.getName())));

        mockMvc.perform(
                get("/projects").param("name", "Project Two"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(projectTwo.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(projectTwo.getName())));

        mockMvc.perform(
                get("/projects").param("name", "Project X"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private ProjectResponse createOneProject() {
        return createProject("project", 1).get(0);
    }

    private List<ProjectResponse> createProject(String prefix, int numberOfProject) {
        List<ProjectResponse> listOfResponse = IntStream.range(0, numberOfProject)
                .mapToObj(num -> createOneProjectWithName(format("%s-%d", prefix, num + 1)))
                .collect(Collectors.toList());

        return listOfResponse;
    }

    private ProjectResponse createOneProjectWithName(String name) {
        try {
            MvcResult result = mockMvc.perform(
                    post("/projects")
                            .contentType(APPLICATION_JSON)
                            .content(project(name)))
                    .andExpect(status().isCreated())
                    .andReturn();

            String contentResponse = result.getResponse().getContentAsString();
            ProjectResponse project = ProjectModelMapper.jsonToProject(contentResponse);

            return project;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Request to Create Project with name" + name, e);
        }
    }
}