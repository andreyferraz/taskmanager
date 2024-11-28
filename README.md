# Gerenciador de Tarefas com Swing
Este é um aplicativo de gerenciamento de tarefas simples utilizando Java, Spring Framework e Swing para a interface gráfica. O projeto permite adicionar, editar e remover tarefas de uma lista.

![img](https://)

### Funcionalidades
* **Adicionar Tarefas:** Permite ao usuário adicionar uma nova tarefa à lista.
* **Editar Tarefas:** O usuário pode editar a descrição de uma tarefa já existente.
* **Remover Tarefas:** Permite excluir uma tarefa da lista.

### Tecnologias Utilizadas
* **Java 11:** Linguagem principal do projeto.
* **Spring Framework:** Para gerenciar o contexto de aplicação e dependências.
* **Swing:** Para a criação da interface gráfica de usuário (GUI).

### Estrutura do Projeto
O projeto é dividido em diferentes pacotes:
1. **com.example.taskmanager.view**

Contém as classes responsáveis pela interface gráfica do usuário.
- TaskmanagerApplication: A classe principal que inicializa a aplicação e exibe a interface gráfica.
- EditTaskView: A janela responsável por editar a descrição de uma tarefa existente.

2. **com.example.taskmanager.model**
Contém as classes de modelo, incluindo a classe Task, que representa as tarefas.

3. **com.example.taskmanager.service**
Contém o serviço TaskService, que fornece as funcionalidades de gerenciamento das tarefas (adicionar, editar, remover, listar).

4. **com.example.taskmanager.config**
Contém a configuração do Spring.

### Detalhes da Implementação
- A aplicação utiliza um modelo de task (tarefa) com um campo UUID como identificador único.
- As tarefas são gerenciadas por meio do TaskService, que é responsável por adicionar, editar e remover tarefas.
- As funcionalidades de edição e remoção são integradas à interface gráfica utilizando a biblioteca Swing.