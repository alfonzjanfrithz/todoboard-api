package com.alfonzjanfrithz.todoboard.todoboardapi.controller;

import com.alfonzjanfrithz.project.todoboard.api.ProjectsApi;
import com.alfonzjanfrithz.project.todoboard.model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class ProjectController implements ProjectsApi {
    @Override
    public ResponseEntity<List<Project>> searchProjects() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
