package br.lucas.olivier.pixsimulator.msconta.infra.gateways;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix.ChavePixJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix.ChavePixWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

public class ChavePixGatewayImplTest {

    private ChavePixJpaRepository chavePixJpaRepository;
    private ChavePixGatewayImpl chavePixGatewayImpl;

    @BeforeEach
    void setUp() {
        chavePixJpaRepository = Mockito.mock(ChavePixJpaRepository.class);
        chavePixGatewayImpl = new ChavePixGatewayImpl(chavePixJpaRepository);
    }

    @Test
    void deveRetornarChavePixPorId() {
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF, "1");
        Mockito.when(chavePixJpaRepository.findByChave(anyString()))
                .thenReturn(Optional.of(ChavePixWrapper.toJpaEntity(chavePix)));

        Optional<ChavePix> result = chavePixGatewayImpl.findByChave("1");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(chavePix, result.get());
    }

    @Test
    void deveSalvarChavePix() {
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF, "1");
        Mockito.when(chavePixJpaRepository.saveAndFlush(Mockito.any())).thenReturn(ChavePixWrapper.toJpaEntity(chavePix));

        Optional<ChavePix> result = chavePixGatewayImpl.save(chavePix);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(chavePix, result.get());
    }

    @Test
    void deveRetornarTodasAsChavesPix() {
        ChavePix chavePix1 = new ChavePix("12345678909", TipoChave.CPF, "1");
        ChavePix chavePix2 = new ChavePix("98765432109", TipoChave.CPF, "2");
        List<ChavePix> chavesPix = Arrays.asList(chavePix1, chavePix2);

        Mockito.when(chavePixJpaRepository.findAllByContaBancariaId(anyString()))
                .thenReturn(List.of(ChavePixWrapper.toJpaEntity(chavePix1), ChavePixWrapper.toJpaEntity(chavePix2)));

        List<ChavePix> result = chavePixGatewayImpl.findAllByContaBancariaId("1");

        Assertions.assertEquals(chavesPix, result);
    }
}