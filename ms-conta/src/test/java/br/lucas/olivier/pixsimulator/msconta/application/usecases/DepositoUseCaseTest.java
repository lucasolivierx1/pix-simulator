package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.infra.kafka.PixSimulatorKafkaProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepositoUseCaseTest {

    @Mock
    private ContaBancariaGateway contaBancariaGateway;

    @Mock
    private PixSimulatorKafkaProducer pixSimulatorKafkaProducer;

    private DepositoUseCase depositoUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        depositoUseCase = new DepositoUseCase(contaBancariaGateway, pixSimulatorKafkaProducer);
    }

    @Test
    public void testExecute() {
        // Arrange
        ContaBancaria contaBancaria = new ContaBancaria("1","1","2", TipoConta.CORRENTE);
        BigDecimal valorDeposito = BigDecimal.valueOf(100);

        when(contaBancariaGateway.findById("1")).thenReturn(Optional.of(contaBancaria));

        // Act
        depositoUseCase.execute("1", valorDeposito);

        // Assert
        verify(contaBancariaGateway).save(contaBancaria);
        Assertions.assertEquals(contaBancaria.getSaldo(), BigDecimal.valueOf(100));
    }
}
