package com.company.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.company.projectmanagement.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId, Sort sort);

    List<Task> findByProjectUserUsernameAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String username, String title, String description);
}
