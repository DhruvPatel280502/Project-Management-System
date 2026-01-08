package com.company.projectmanagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.projectmanagement.dto.TaskDto;
import com.company.projectmanagement.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	
    @Autowired
    private TaskService taskService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<?> createTask(@PathVariable Long projectId,
                                        @Valid @RequestBody TaskDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(taskService.create(projectId, dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getTasks(@PathVariable Long projectId,
                                      @RequestParam String sortBy) {
        try {
            return ResponseEntity.ok(
                    taskService.list(projectId, sortBy)
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTasks(@RequestParam String keyword,
                                         Principal principal) {
        try {
            return ResponseEntity.ok(
                    taskService.search(keyword, principal.getName())
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId,
                                        @Valid @RequestBody TaskDto dto,
                                        Principal principal) {
        try {
            return ResponseEntity.ok(
                    taskService.update(taskId, dto, principal.getName())
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId,
                                        Principal principal) {
        try {
            taskService.delete(taskId, principal.getName());
            return ResponseEntity.ok("Task deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
    
}
