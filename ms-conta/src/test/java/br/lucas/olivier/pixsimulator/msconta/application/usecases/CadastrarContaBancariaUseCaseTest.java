package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class CadastrarContaBancariaUseCaseTest {
    ContaBancariaGateway contaBancariaGateway;

    @BeforeEach
    void beforeEach() {
        contaBancariaGateway = Mockito.mock(ContaBancariaGateway.class);
    }

    @Test
    void deveCadastrarContaBancaria() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);

        Mockito.when(contaBancariaGateway.save(Mockito.any(ContaBancaria.class))).thenReturn(Optional.of(contaBancaria));

        CadastrarContaBancariaUseCase useCase = new CadastrarContaBancariaUseCase(contaBancariaGateway);
        ContaBancaria result = useCase.execute("1234", "56789", "0", TipoConta.CORRENTE);

        Assertions.assertEquals(contaBancaria, result);
    }

    @Test
    void naoDeveCadastrarContaBancariaQuandoOcorreErro() {

        Mockito.when(contaBancariaGateway.save(Mockito.any(ContaBancaria.class)))
                .thenReturn(Optional.empty());

        CadastrarContaBancariaUseCase useCase = new CadastrarContaBancariaUseCase(contaBancariaGateway);

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            useCase.execute("1234", "56789", "0", TipoConta.CORRENTE);
        });
    }
}
