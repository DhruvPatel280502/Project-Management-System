package com.company.projectmanagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.company.projectmanagement.dto.TaskDto;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.repository.ProjectRepository;
import com.company.projectmanagement.repository.TaskRepository;
import com.company.projectmanagement.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper mapper;
    
    @Override
    public TaskDto create(Long projectId, TaskDto dto) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = mapper.map(dto, Task.class);
        task.setProject(project);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return mapper.map(taskRepository.save(task), TaskDto.class);
    }
    
    @Override
    public List<TaskDto> list(Long projectId, String sortBy) {
        return taskRepository.findByProjectId(projectId, Sort.by(sortBy))
                .stream()
                .map(t -> mapper.map(t, TaskDto.class))
                .toList();
    }

    public List<TaskDto> search(String keyword, String username) {
        return taskRepository
                .findByProjectUserUsernameAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        username, keyword, keyword)
                .stream()
                .map(t -> mapper.map(t, TaskDto.class))
                .toList();
    }
    
    @Override
    public TaskDto update(Long taskId, TaskDto dto, String username) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        
        if (!task.getProject().getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to update this task");
        }

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        return mapper.map(taskRepository.save(task), TaskDto.class);
    }

    @Override
    public void delete(Long taskId, String username) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getProject().getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to delete this task");
        }

        taskRepository.delete(task);
    }

}
