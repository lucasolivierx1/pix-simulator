package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class BuscaContasBancariasUseCaseTest {
    ContaBancariaGateway contaBancariaGateway;

    @BeforeEach
    void beforeEach() {
        contaBancariaGateway = Mockito.mock(ContaBancariaGateway.class);
    }

    @Test
    void deveRetornarContasBancarias() {

        ContaBancaria contaBancaria1 = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ContaBancaria contaBancaria2 = new ContaBancaria("9876", "54321", "1", TipoConta.CORRENTE);
        List<ContaBancaria> contasBancarias = Arrays.asList(contaBancaria1, contaBancaria2);

        Mockito.when(contaBancariaGateway.findAll()).thenReturn(contasBancarias);

        BuscaContasBancariasUseCase useCase = new BuscaContasBancariasUseCase(contaBancariaGateway);
        List<ContaBancaria> result = useCase.execute();

        Assertions.assertEquals(contasBancarias, result);
    }

    @Test
    void naoDeveRetornarContasBancariasQuandoNaoExistem() {
        Mockito.when(contaBancariaGateway.findAll()).thenReturn(List.of());

        BuscaContasBancariasUseCase useCase = new BuscaContasBancariasUseCase(contaBancariaGateway);

        var contasBancarias = useCase.execute();

        Assertions.assertTrue(contasBancarias.isEmpty(), "Deveria retornar lista vazia");
    }
}