package com.example.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.taskmanager.config.SpringConfig;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;

@SpringBootApplication
public class TaskmanagerApplication {

    public static void main(String[] args) {
        // Inicializa o contexto do Spring
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        TaskService taskService = context.getBean(TaskService.class);

        // Cria a interface gráfica
        SwingUtilities.invokeLater(() -> createAndShowGUI(taskService));
    }

    private static void createAndShowGUI(TaskService taskService) {
        JFrame frame = new JFrame("Gerenciador de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Define o tamanho da janela

        // Painel principal com layout
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Modelo da lista de tarefas
        DefaultListModel<String> taskListModel = new DefaultListModel<>();
        JList<String> taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Campo de texto e botões
        JTextField taskField = new JTextField();
        JButton addButton = new JButton("Adicionar");
        JButton removeButton = new JButton("Remover");
        JButton editButton = new JButton("Editar");  // Botão de editar

        // Painel para o campo de entrada e botão adicionar
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Adiciona os componentes ao painel principal
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(removeButton, BorderLayout.SOUTH);
        panel.add(editButton, BorderLayout.WEST);  // Adiciona o botão de editar

        // Atualiza a lista de tarefas ao abrir
        updateTaskList(taskListModel, taskService);

        // Adiciona ação para o botão "Adicionar"
        addButton.addActionListener(e -> {
            String description = taskField.getText().trim();
            if (!description.isEmpty()) {
                taskService.addTask(description);
                updateTaskList(taskListModel, taskService);
                taskField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "A tarefa não pode estar vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adiciona ação para o botão "Remover"
        removeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                List<Task> tasks = taskService.getAllTasks();
                taskService.removeTask(tasks.get(selectedIndex).getId());
                updateTaskList(taskListModel, taskService);
            } else {
                JOptionPane.showMessageDialog(frame, "Nenhuma tarefa selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

// Adiciona ação para o botão "Editar"
editButton.addActionListener(e -> {
    int selectedIndex = taskList.getSelectedIndex();
    if (selectedIndex != -1) {
        // Pega a tarefa selecionada
        List<Task> tasks = taskService.getAllTasks();
        Task selectedTask = tasks.get(selectedIndex);

        // Abre a janela de edição, passando o UUID da tarefa
        EditTaskView editTaskView = new EditTaskView(taskService, selectedTask.getId(), () -> updateTaskList(taskListModel, taskService));
        editTaskView.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(frame, "Nenhuma tarefa selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
});


        // Adiciona o painel ao frame
        frame.getContentPane().add(panel);

        // Define visibilidade e posição central
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
    }

    private static void updateTaskList(DefaultListModel<String> taskListModel, TaskService taskService) {
        taskListModel.clear();
        for (Task task : taskService.getAllTasks()) {
            taskListModel.addElement(task.getDescription());
        }
    }
}
