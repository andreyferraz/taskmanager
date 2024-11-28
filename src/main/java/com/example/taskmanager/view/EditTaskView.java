package com.example.taskmanager.view;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class EditTaskView extends JFrame {

    private final TaskService taskService;
    private UUID taskId;
    private JTextField descriptionField;
    private JButton saveButton;
    private Runnable updateCallback;  // Callback para atualizar a lista

    public EditTaskView(TaskService taskService, UUID taskId, Runnable updateCallback) {
        this.taskService = taskService;
        this.taskId = taskId;
        this.updateCallback = updateCallback;  // Recebe o callback

        // Configuração da janela
        setTitle("Editar Tarefa");
        setSize(400, 200);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout
        setLayout(new FlowLayout());

        // Label e campo de texto para a descrição
        JLabel descriptionLabel = new JLabel("Descrição da Tarefa:");
        descriptionField = new JTextField(20);
        descriptionField.setText(getTaskDescription(taskId));  // Preenche com a descrição atual da tarefa

        // Botão de salvar
        saveButton = new JButton("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTaskDescription();
            }
        });

        // Adicionando componentes à janela
        add(descriptionLabel);
        add(descriptionField);
        add(saveButton);
    }

    // Método para carregar a descrição atual da tarefa
    private String getTaskDescription(UUID taskId) {
        Task task = taskService.getAllTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);

        return (task != null) ? task.getDescription() : "";
    }

    // Método para salvar a tarefa com a nova descrição
    private void saveTaskDescription() {
        String newDescription = descriptionField.getText();
        try {
            taskService.editTask(taskId, newDescription); // Chama o serviço para atualizar a tarefa
            JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            updateCallback.run(); // Atualiza a lista na tela principal
            dispose(); // Fecha a janela de edição
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Simulando a criação da janela de edição
        UUID taskId = UUID.randomUUID();  // Simulando um ID de tarefa
        TaskService taskService = new TaskService(null); // Passar o repositório real
        EditTaskView editTaskView = new EditTaskView(taskService, taskId, () -> {
            // Simulação de callback
            System.out.println("Lista de tarefas atualizada.");
        });
        editTaskView.setVisible(true);
    }
}
