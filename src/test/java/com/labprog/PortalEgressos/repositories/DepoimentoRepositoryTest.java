package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
public class DepoimentoRepositoryTest {

    @Autowired
    DepoimentoRepository depoimentoRepository;

    @Autowired
    EgressoRepository egressoRepository;

    @Test @Transactional
    public void deveSalvarDepoimento(){
        Egresso egresso = Egresso.builder()
                .nome("Huehue")
                .descricao("jdsaoijda")
                .email("jdsaoijdsaoi@mga.com")
                .build();

        egressoRepository.save(egresso);

        Depoimento depoimento = Depoimento.builder()
                .texto("Textin")
                .egresso(egresso)
                .data(new Date(1L))
                .build();

        var salvado = depoimentoRepository.save(depoimento);

        Assertions.assertNotNull(salvado);

        Assertions.assertEquals(depoimento.getId(), salvado.getId());
        Assertions.assertEquals(depoimento.getTexto(), salvado.getTexto());
        Assertions.assertEquals(salvado.getEgresso().getId(), depoimento.getEgresso().getId());
        Assertions.assertEquals(salvado.getEgresso().getId(), egresso.getId());
    }
}
