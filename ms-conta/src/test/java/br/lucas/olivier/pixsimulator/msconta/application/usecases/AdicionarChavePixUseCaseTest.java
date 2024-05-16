package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class AdicionarChavePixUseCaseTest {
    ContaBancariaGateway contaBancariaGateway;
    ChavePixGateway chavePixGateway;

    @BeforeEach
    void beforeEach() {
        contaBancariaGateway = Mockito.mock(ContaBancariaGateway.class);
        chavePixGateway = Mockito.mock(ChavePixGateway.class);
    }

    @Test
    void deveAdicionarChavePix() {

        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF, "1");

        Mockito.when(contaBancariaGateway.findById(Mockito.anyString()))
                .thenReturn(Optional.of(contaBancaria));

        Mockito.when(chavePixGateway.save(Mockito.any(ChavePix.class)))
                .thenReturn(Optional.of(chavePix));

        AdicionarChavePixUseCase useCase = new AdicionarChavePixUseCase(chavePixGateway, contaBancariaGateway);
        useCase.execute("1", "12345678909", TipoChave.CPF);

        Assertions.assertTrue(contaBancaria.getChavesPix().contains(chavePix), "Chave pix não foi adicionada");
    }

    @Test
    void naoDeveAdicionarChavePixQuandoContaBancariaNaoExiste() {

        Mockito.when(contaBancariaGateway.findById(Mockito.anyString())).thenReturn(Optional.empty());

        AdicionarChavePixUseCase useCase = new AdicionarChavePixUseCase(chavePixGateway, contaBancariaGateway);

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            useCase.execute("1", "12345678909", TipoChave.CPF);
        }, "Deveria lançar exceção quando a conta bancária não existe");
    }

    @Test
    void naoDeveAdicionarChavePixQuandoOcorreErroAoSalvar() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);

        Mockito.when(contaBancariaGateway.findById(Mockito.anyString()))
                .thenReturn(Optional.of(contaBancaria));

        Mockito.when(chavePixGateway.save(Mockito.any(ChavePix.class)))
                .thenReturn(Optional.empty());

        AdicionarChavePixUseCase useCase = new AdicionarChavePixUseCase(chavePixGateway, contaBancariaGateway);

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            useCase.execute("1", "12345678909", TipoChave.CPF);
        }, "Deveria lançar exceção quando ocorre erro ao salvar a chave pix");
    }
}
