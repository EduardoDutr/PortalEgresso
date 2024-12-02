package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test @Transactional
    public void deveSalvarCurso(){
        Curso curso = Curso.builder()
                .nome("Comp")
                .nivel("Alto")
                .build();

        var salvado = cursoRepository.save(curso);

        Assertions.assertNotNull(salvado);

        Assertions.assertEquals(curso.getId(), salvado.getId());
        Assertions.assertEquals(curso.getNome(), salvado.getNome());
        Assertions.assertEquals(curso.getNivel(), salvado.getNivel());
    }


}
