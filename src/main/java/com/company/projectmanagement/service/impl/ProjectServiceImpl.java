package com.company.projectmanagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.projectmanagement.dto.ProjectDto;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.repository.ProjectRepository;
import com.company.projectmanagement.repository.UserRepository;
import com.company.projectmanagement.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    
    @Override
    public ProjectDto create(ProjectDto dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = mapper.map(dto, Project.class);
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());

        return mapper.map(projectRepository.save(project), ProjectDto.class);
    }
    
    @Override
    public List<ProjectDto> getAll(String username) {
        return projectRepository.findByUserUsername(username)
                .stream()
                .map(p -> mapper.map(p, ProjectDto.class))
                .toList();
    }
    
    @Override
    public ProjectDto update(Long projectId, ProjectDto dto, String username) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        
        if (!project.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to update this project");
        }

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setUpdatedAt(LocalDateTime.now());

        return mapper.map(projectRepository.save(project), ProjectDto.class);
    }

    @Override
    public void delete(Long projectId, String username) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to delete this project");
        }

        projectRepository.delete(project);
    }
    
    

}
