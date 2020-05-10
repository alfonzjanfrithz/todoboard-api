package com.alfonzjanfrithz.todoboard.todoboardapi.repository;

import com.alfonzjanfrithz.todoboard.todoboardapi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByName(String name);
}
