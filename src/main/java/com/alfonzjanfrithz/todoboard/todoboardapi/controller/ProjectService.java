package com.alfonzjanfrithz.todoboard.todoboardapi.controller;

import com.alfonzjanfrithz.todoboard.todoboardapi.entities.Project;
import com.alfonzjanfrithz.todoboard.todoboardapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> retrieveAllProjects() {
        return projectRepository.findAll();
    }

    public Project findProject(Long projectId) {
        return projectRepository.getOne(projectId);
    }

    public List<Project> findProjectsByName(String name) {
        return projectRepository.findAllByName(name);
    }
}
