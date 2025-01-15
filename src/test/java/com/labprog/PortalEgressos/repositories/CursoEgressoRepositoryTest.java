package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CursoEgressoRepositoryTest {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoEgressoRepository cursoEgressoRepository;

    @Autowired
    private EgressoRepository egressoRepository;

    @Test @Transactional
    public void deveSalvarCursoEgresso(){
        Egresso egresso = Egresso.builder()
                .nome("Huehue")
                .descricao("jdsaoijda")
                .email("jdsaoijdsaoi@mga.com")
                .build();

        egressoRepository.save(egresso);

        Curso curso = Curso.builder()
                .nome("Comp")
                .nivel("Alto")
                .build();

        cursoRepository.save(curso);

        CursoEgresso cursoEgresso = CursoEgresso.builder()
                .egresso(egresso)
                .curso(curso)
                .anoInicio(1)
                .build();

        var salvado = cursoEgressoRepository.save(cursoEgresso);

        Assertions.assertNotNull(salvado);

        Assertions.assertEquals(cursoEgresso.getId(), salvado.getId());
        Assertions.assertEquals(cursoEgresso.getEgresso(), salvado.getEgresso());
        Assertions.assertEquals(cursoEgresso.getCurso(), salvado.getCurso());
        Assertions.assertEquals(cursoEgresso.getAnoInicio(), salvado.getAnoInicio());
    }

    @Test @Transactional
    public void deveObterCursoEgresso(){
        Egresso egresso = Egresso.builder()
                .nome("Huehue")
                .descricao("jdsaoijda")
                .email("jdsaoijdsaoi@mga.com")
                .build();

        egressoRepository.save(egresso);

        Curso curso = Curso.builder()
                .nome("Comp")
                .nivel("Alto")
                .build();

        cursoRepository.save(curso);

        CursoEgresso cursoEgresso = CursoEgresso.builder()
                .egresso(egresso)
                .curso(curso)
                .anoInicio(1)
                .build();

        cursoEgressoRepository.save(cursoEgresso);
        var salvado = cursoEgressoRepository.getReferenceById(cursoEgresso.getId());

        Assertions.assertNotNull(salvado);

        Assertions.assertEquals(cursoEgresso.getId(), salvado.getId());
        Assertions.assertEquals(cursoEgresso.getEgresso(), salvado.getEgresso());
        Assertions.assertEquals(cursoEgresso.getCurso(), salvado.getCurso());
        Assertions.assertEquals(cursoEgresso.getAnoInicio(), salvado.getAnoInicio());
    }
}
