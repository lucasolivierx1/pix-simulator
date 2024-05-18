package br.lucas.olivier.pixsimulator.extrato.application.usecases;

import br.lucas.olivier.pixsimulator.extrato.application.dtos.ResumoExtratoDTO;
import br.lucas.olivier.pixsimulator.extrato.application.gateways.ExtratoGateway;
import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;
import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GerarExtratoUseCaseTest {

    private ExtratoGateway extratoGateway;
    private GerarExtratoUseCase gerarExtratoUseCase;

    @BeforeEach
    public void setup() {
        extratoGateway = Mockito.mock(ExtratoGateway.class);
        gerarExtratoUseCase = new GerarExtratoUseCase(extratoGateway);
    }

    @Test
    public void testExecute() {
        String idConta = "1";
        Extrato extrato1 = new Extrato(idConta,  LocalDateTime.now(), "Teste", TipoMovimentoEnum.CREDITO, BigDecimal.valueOf(100));
        Extrato extrato2 = new Extrato(idConta,  LocalDateTime.now(), "Teste", TipoMovimentoEnum.DEBITO, BigDecimal.valueOf(50));
        List<Extrato> extratos = Arrays.asList(extrato1, extrato2);

        when(extratoGateway.getExtrato(idConta)).thenReturn(extratos);

        ResumoExtratoDTO resumoExtratoDTO = gerarExtratoUseCase.execute(idConta);

        assertEquals(BigDecimal.valueOf(50), resumoExtratoDTO.saldoAtual());
        assertEquals(2, resumoExtratoDTO.movimentacoes().size());
    }
}