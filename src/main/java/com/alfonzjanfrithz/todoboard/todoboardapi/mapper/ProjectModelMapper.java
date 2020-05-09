package com.alfonzjanfrithz.todoboard.todoboardapi.mapper;

import com.alfonzjanfrithz.project.todoboard.model.ProjectRequest;
import com.alfonzjanfrithz.project.todoboard.model.ProjectResponse;
import com.alfonzjanfrithz.todoboard.todoboardapi.entities.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectModelMapper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Project toEntity(ProjectRequest projectRequest) {
        return Project.builder()
                .name(projectRequest.getName())
                .build();
    }

    public static ProjectResponse toApi(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setName(project.getName());

        return projectResponse;
    }

    public static List<ProjectResponse> toApi(List<Project> retrieveAllProjects) {
        return retrieveAllProjects.stream()
                .map(ProjectModelMapper::toApi)
                .collect(Collectors.toList());
    }

    public static ProjectResponse jsonToProject(String json) throws JsonProcessingException {
        return mapper.readValue(json, ProjectResponse.class);
    }
}