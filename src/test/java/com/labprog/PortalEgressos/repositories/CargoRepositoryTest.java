package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CargoRepositoryTest {
    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private EgressoRepository egressoRepository;

    @Test @Transactional
    public void deveSalvarCargo(){
        Egresso egresso = Egresso.builder()
                .nome("Huehue")
                .descricao("jdsaoijda")
                .email("jdsaoijdsaoi@mga.com")
                .build();

        Cargo cargo = Cargo.builder()
                .descricao("NoSei")
                .local("FUMA")
                .anoInicio(1L)
                .egresso(egresso)
                .build();

        egressoRepository.save(egresso);

        Cargo salvado = cargoRepository.save(cargo);

        Assertions.assertNotNull(salvado);

        Assertions.assertEquals(cargo.getId(), salvado.getId());
        Assertions.assertEquals(cargo.getDescricao(), salvado.getDescricao());
        Assertions.assertEquals(cargo.getLocal(), salvado.getLocal());
    }

    @Test @Transactional
    public void deveObterEgressoPartindoDeCargo(){
        Egresso egresso = Egresso.builder()
                .nome("Huehue")
                .email("jdsaoijdsaoi@mga.com")
                .descricao("ConfirmaEu")
                .build();

        Cargo cargo = Cargo.builder()
                .egresso(egresso)
                .descricao("hahahah")
                .local("FUMA")
                .anoInicio(1L)
                .build();

        egressoRepository.save(egresso);

        cargoRepository.save(cargo);

        var egressoDoBanco = egressoRepository.getReferenceById(cargo.getEgresso().getId());

        Assertions.assertNotNull(egressoDoBanco);

        Assertions.assertEquals(egresso.getId(), egressoDoBanco.getId());
        Assertions.assertEquals(egresso.getDescricao(), egressoDoBanco.getDescricao());

        Assertions.assertEquals("ConfirmaEu", egressoDoBanco.getDescricao());
    }
}
