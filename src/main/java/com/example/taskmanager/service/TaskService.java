package com.example.taskmanager.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da tarefa não pode estar vazia.");
        }
        return taskRepository.save(new Task(description));
    }

    public void removeTask(UUID id) {
        taskRepository.deleteById(id);
    }

    public Task editTask(UUID id, String newDescription) {
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da tarefa não pode estar vazia.");
        }

        // Verificando se a tarefa existe
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setDescription(newDescription); // Atualiza a descrição da tarefa
            return taskRepository.save(task); // Salva a tarefa atualizada
        } else {
            throw new IllegalArgumentException("Tarefa com o ID fornecido não encontrada.");
        }
    }
}
