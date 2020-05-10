package com.alfonzjanfrithz.todoboard.todoboardapi.controller;

import com.alfonzjanfrithz.project.todoboard.api.ProjectsApi;
import com.alfonzjanfrithz.project.todoboard.model.ProjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class ProjectController implements ProjectsApi {
    @Override
    public ResponseEntity<List<ProjectResponse>> searchProjects(@Valid String name) {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
