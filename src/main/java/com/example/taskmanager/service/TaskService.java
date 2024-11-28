package com.example.taskmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task addTask(String description){
        if(description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("Descrição da tarefa não pode estar vazia.");
        }
        return taskRepository.save(new Task());
    }

    public void removeTask(UUID id){
        taskRepository.deleteById(id);
    }
}
