package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.infra.kafka.PixSimulatorKafkaProducer;
import br.lucas.olivier.pixsimulator.msconta.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TransferenciaPixUseCaseTest {

    @Mock
    private ChavePixGateway chavePixGateway;

    @Mock
    private ContaBancariaGateway contaBancariaGateway;

    @Mock
    private PixSimulatorKafkaProducer pixSimulatorKafkaProducer;

    @InjectMocks
    JsonUtils jsonUtils;

    private TransferenciaPixUseCase transferenciaPixUseCase;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transferenciaPixUseCase = new TransferenciaPixUseCase(chavePixGateway,
                contaBancariaGateway, pixSimulatorKafkaProducer);
    }

    @Test
    public void testExecute() {
        // Arrange
        ContaBancaria origem = new ContaBancaria("1", "1", "1", "1", TipoConta.CORRENTE);
        ContaBancaria destino = new ContaBancaria("2", "2", "2", "2", TipoConta.CORRENTE);

        ChavePix chavePix = new ChavePix("teste@teste.com", TipoChave.EMAIL, "2");

        origem.creditar(BigDecimal.valueOf(100));

        BigDecimal valor = BigDecimal.valueOf(100);

        when(chavePixGateway.findByChave("teste@teste.com")).thenReturn(Optional.of(chavePix));
        when(contaBancariaGateway.findById("1")).thenReturn(Optional.of(origem));
        when(contaBancariaGateway.findById("2")).thenReturn(Optional.of(destino));

        // Act
        transferenciaPixUseCase.execute(origem.getId(), "teste@teste.com", valor);

        // Assert
        Assertions.assertEquals(origem.getSaldo(), BigDecimal.ZERO);
        Assertions.assertEquals(destino.getSaldo(), BigDecimal.valueOf(100));

        verify(contaBancariaGateway).save(origem);
        verify(contaBancariaGateway).save(destino);
        verify(pixSimulatorKafkaProducer, times(2)).sendEvent(anyString(), anyString());
    }
}