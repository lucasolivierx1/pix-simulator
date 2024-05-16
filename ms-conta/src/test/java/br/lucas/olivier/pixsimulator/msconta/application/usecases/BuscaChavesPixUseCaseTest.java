package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class BuscaChavesPixUseCaseTest {
    ChavePixGateway chavePixGateway;

    @BeforeEach
    void beforeEach() {
        chavePixGateway = Mockito.mock(ChavePixGateway.class);
    }

    @Test
    void deveRetornarChavesPix() {

        ChavePix chavePix1 = new ChavePix("12345678909", TipoChave.CPF, "1");
        ChavePix chavePix2 = new ChavePix("98765432109", TipoChave.CPF, "2");
        List<ChavePix> chavesPix = Arrays.asList(chavePix1, chavePix2);

        Mockito.when(chavePixGateway.findAllByContaBancariaId(Mockito.anyString())).thenReturn(chavesPix);

        BuscaChavesPixUseCase useCase = new BuscaChavesPixUseCase(chavePixGateway);
        List<ChavePix> result = useCase.execute("1");

        Assertions.assertEquals(chavesPix, result);
    }

    @Test
    void naoDeveRetornarChavesPixQuandoContaBancariaNaoExiste() {
        Mockito.when(chavePixGateway.findAllByContaBancariaId(Mockito.anyString())).thenReturn(List.of());

        BuscaChavesPixUseCase useCase = new BuscaChavesPixUseCase(chavePixGateway);

        var chavesPix = useCase.execute("-1");

        Assertions.assertTrue(chavesPix.isEmpty(), "Deveria retornar lista vazia");
    }
}