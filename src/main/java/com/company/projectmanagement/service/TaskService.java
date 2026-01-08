package com.company.projectmanagement.service;

import java.util.List;

import com.company.projectmanagement.dto.TaskDto;

public interface TaskService {

	TaskDto create(Long projectId, TaskDto dto);

	List<TaskDto> list(Long projectId, String sortBy);

	List<TaskDto> search(String keyword, String username);
	
    TaskDto update(Long taskId, TaskDto dto, String username);

    void delete(Long taskId, String username);


}
