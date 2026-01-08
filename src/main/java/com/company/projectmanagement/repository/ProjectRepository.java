package com.company.projectmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.projectmanagement.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserUsername(String username);
}
