package psii.senai.appescola.spring_boot_escola.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String matricula;
    private String curso;
    private Integer idade;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getMatricula() {return matricula;}
    public void setMatricula(String matricula) {this.matricula = matricula;}
    public String getCurso() {return curso;}
    public void setCurso(String curso) {this.curso = curso;}
    public Integer getIdade() {return idade;}
    public void setIdade(Integer idade) {this.idade = idade;}

    
}
