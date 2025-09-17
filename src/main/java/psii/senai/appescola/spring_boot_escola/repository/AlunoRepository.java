package psii.senai.appescola.spring_boot_escola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psii.senai.appescola.spring_boot_escola.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
