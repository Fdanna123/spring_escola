README - Projeto: Cadastro de Alunos (Spring Boot)

Projeto web para cadastro e listagem de alunos usando Spring Boot, Thymeleaf e MySQL.
(Instruções passo a passo para configurar, rodar e entregar o trabalho)

Tecnologias

Java 17+

Spring Boot 3.x

Spring Data JPA

Thymeleaf

MySQL (ou MariaDB)

Maven

Estrutura esperada do projeto
psii.senai.appescola
├── controller
│   └── AlunoController.java
├── model
│   └── Aluno.java
├── repository
│   └── AlunoRepository.java
└── src/main/resources
    └── templates
        └── alunos.html

Pré-requisitos (instalar antes)

Java JDK 17+ instalado e JAVA_HOME configurado.

Maven instalado (mvn no PATH).

MySQL (ou XAMPP/MAMP que inclua MySQL) instalado e em execução.

(Opcional) MySQL Workbench para executar comandos SQL.

IDE (IntelliJ, VS Code, Eclipse) — opcional mas recomendado.

1) Criar o banco de dados

Abra MySQL (Workbench, terminal ou XAMPP) e execute:

CREATE DATABASE dbescola CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


Observação: o projeto usa dbescola por padrão (conforme o enunciado). Se preferir outro nome, altere spring.datasource.url no application.properties.

2) Configurar application.properties

Crie/edite src/main/resources/application.properties com (exemplo):

# Dados da conexão com o MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/dbescola?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Porta (opcional)
server.port=8080


Se usar senha no MySQL, coloque em spring.datasource.password.

ddl-auto=update faz o Hibernate criar/atualizar tabelas automaticamente (útil para dev).

3) Entidade Aluno (resumo)

src/main/java/psii/senai/appescola/model/Aluno.java — exemplo mínimo:

package psii.senai.appescola.model;

import jakarta.persistence.*;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String matricula;
    private String curso;
    private Integer idade;

    // getters e setters (ou @Data do Lombok se usar Lombok)
}

4) Repositório

src/main/java/psii/senai/appescola/repository/AlunoRepository.java:

package psii.senai.appescola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psii.senai.appescola.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> { }

5) Controller

src/main/java/psii/senai/appescola/controller/AlunoController.java:

package psii.senai.appescola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import psii.senai.appescola.model.Aluno;
import psii.senai.appescola.repository.AlunoRepository;

@Controller
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/alunos")
    public String index(Model model) {
        model.addAttribute("alunos", repository.findAll());
        model.addAttribute("aluno", new Aluno());
        return "alunos";
    }

    @PostMapping("/alunos/salvar")
    public String salvar(@ModelAttribute Aluno aluno) {
        repository.save(aluno);
        return "redirect:/alunos";
    }
}

6) Template Thymeleaf

Crie src/main/resources/templates/alunos.html (conforme enunciado). Exemplo simples:

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Cadastro de Alunos</title>
</head>
<body>
  <h1>Cadastro de Alunos</h1>

  <form th:action="@{/alunos/salvar}" th:object="${aluno}" method="post">
      Nome: <input type="text" th:field="*{nome}" required><br>
      Matrícula: <input type="text" th:field="*{matricula}" required><br>
      Curso: <input type="text" th:field="*{curso}" required><br>
      Idade: <input type="number" th:field="*{idade}" required><br>
      <button type="submit">Salvar</button>
  </form>

  <h2>Lista de Alunos</h2>
  <table border="1">
      <tr><th>ID</th><th>Nome</th><th>Matrícula</th><th>Curso</th><th>Idade</th></tr>
      <tr th:each="a : ${alunos}">
          <td th:text="${a.id}"></td>
          <td th:text="${a.nome}"></td>
          <td th:text="${a.matricula}"></td>
          <td th:text="${a.curso}"></td>
          <td th:text="${a.idade}"></td>
      </tr>
  </table>
</body>
</html>

7) Rodando o projeto localmente (passo a passo)

Clone o repositório (ou crie o projeto com Spring Initializr):

git clone <seu-repo-url>.git
cd nome-do-projeto


Verifique se o MySQL está rodando e o banco dbescola existe.

Ajuste application.properties se necessário.

Rodar com Maven:

mvn clean install
mvn spring-boot:run


ou empacotar e executar o jar:

mvn clean package
java -jar target/seu-artifact-id-0.0.1-SNAPSHOT.jar


Abra no navegador: http://localhost:8080/alunos

8) Testando via curl (envio de formulário)

Exemplo de envio via terminal (simula o form):

curl -X POST http://localhost:8080/alunos/salvar \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "nome=Maria&matricula=2025001&curso=Informática&idade=18"


Depois abra http://localhost:8080/alunos para ver a lista.

9) Script SQL (opcional)

Se preferir incluir um script SQL no repositório (script.sql):

-- script.sql
CREATE DATABASE IF NOT EXISTS dbescola CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dbescola;

-- Abaixo o Hibernate criará a tabela automaticamente, mas você pode criar manualmente se quiser:
CREATE TABLE IF NOT EXISTS aluno (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255),
  matricula VARCHAR(255),
  curso VARCHAR(255),
  idade INT
);

10) Arquivo .gitignore sugerido

Conteúdo mínimo para .gitignore:

/target
/.idea
*.iml
*.log
*.jar
.env
.DS_Store

11) Entrega no GitHub (o que incluir)

No repositório entregue:

Código fonte completo (src/)

pom.xml

README.md (este arquivo)

script.sql (opcional)

Pasta prints/ com prints da aplicação em funcionamento (ex.: tela do formulário preenchido e lista de alunos)

(Opcional) script-export-db.sql com dump do banco se preferir

12) Checklist para avaliação (confirme antes de enviar)

 Projeto executa com sucesso (mvn spring-boot:run).

 Estrutura de pacotes conforme solicitado.

 Entidade Aluno implementada com os campos: id, nome, matricula, curso, idade.

 Conexão com MySQL funcionando e tabelas criadas.

 Interface com formulário e listagem funcionando.

 Prints e README.md no repositório.

 (Opcional) script.sql incluído.

13) Problemas comuns / soluções rápidas

Conexão recusada ao MySQL: verifique se o servidor está rodando (XAMPP/MySQL) e credenciais em application.properties.

Porta 8080 em uso: mude server.port em application.properties.

Hibernate não cria tabelas: verifique spring.jpa.hibernate.ddl-auto=update.

Erro de timezone: adicione ?serverTimezone=UTC na URL JDBC.

Anthony P. e Maria Danna

Data: 16/09/2025
