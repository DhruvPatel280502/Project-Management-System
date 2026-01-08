package com.company.projectmanagement.service;

import java.util.List;

import com.company.projectmanagement.dto.ProjectDto;

public interface ProjectService {
	
	ProjectDto create(ProjectDto dto, String username);
	
    List<ProjectDto> getAll(String username);
    
    ProjectDto update(Long projectId, ProjectDto dto, String username);

    void delete(Long projectId, String username);

}
