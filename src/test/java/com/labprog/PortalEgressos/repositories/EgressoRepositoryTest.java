package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class EgressoRepositoryTest {

    @Autowired
    EgressoRepository egressoRepository;

    @Test @Transactional
    public void deveSalvarEgresso(){
        Egresso egresso = Egresso.builder()
                .nome("Huehue")
                .email("jdsaoijdsaoi@mga.com")
                .descricao("ConfirmaEu")
                .build();

        var salvado = egressoRepository.save(egresso);

        Assertions.assertNotNull(salvado);

        Assertions.assertEquals(egresso.getId(), salvado.getId());
        Assertions.assertEquals(egresso.getNome(), salvado.getNome());
        Assertions.assertEquals(egresso.getDescricao(), salvado.getDescricao());
        Assertions.assertEquals(egresso.getEmail(), salvado.getEmail());
    }
}
