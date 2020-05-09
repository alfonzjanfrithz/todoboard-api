package com.alfonzjanfrithz.todoboard.todoboardapi.controller;

import com.alfonzjanfrithz.project.todoboard.api.ProjectsApi;
import com.alfonzjanfrithz.project.todoboard.model.ProjectRequest;
import com.alfonzjanfrithz.project.todoboard.model.ProjectResponse;
import com.alfonzjanfrithz.todoboard.todoboardapi.entities.Project;
import com.alfonzjanfrithz.todoboard.todoboardapi.mapper.ProjectModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static com.alfonzjanfrithz.todoboard.todoboardapi.mapper.ProjectModelMapper.toApi;
import static org.springframework.http.HttpStatus.CREATED;

@Controller
public class ProjectController implements ProjectsApi {
    @Autowired
    private ProjectService service;

    @Override
    public ResponseEntity<List<ProjectResponse>> searchProjects(@Valid String name) {
        if (name != null) {
            return ResponseEntity.ok(ProjectModelMapper.toApi(service.findProjectsByName(name)));
        } else {
            return ResponseEntity.ok(ProjectModelMapper.toApi(service.retrieveAllProjects()));
        }
    }

    @Override
    public ResponseEntity<ProjectResponse> createProject(@Valid ProjectRequest projectRequest) {
        Project project = service.createProject(ProjectModelMapper.toEntity(projectRequest));
        return ResponseEntity.status(CREATED).body(toApi(project));
    }

    @Override
    public ResponseEntity<ProjectResponse> getProjects(@Min(1L) Long projectId) {
        Project project = service.findProject(projectId);
        return ResponseEntity.ok().body(toApi(project));
    }
}
