package br.lucas.olivier.pixsimulator.extrato.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;
import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;
import br.lucas.olivier.pixsimulator.extrato.domain.exceptions.ExtratoException;

public class ExtratoTest {

    @Test
    public void validaIdConta() {
        assertThrows(ExtratoException.class, () -> {
            new Extrato(null, LocalDateTime.now(), "Teste", TipoMovimentoEnum.CREDITO,
                    BigDecimal.ZERO);
        });
    }


    @Test
    public void ValidaDataHora() {
        assertThrows(ExtratoException.class, () -> {
            new Extrato("1", null, "Teste", TipoMovimentoEnum.CREDITO, BigDecimal.ZERO);
        });
    }

    @Test
    public void validaDescricao() {
        assertThrows(ExtratoException.class, () -> {
            new Extrato("1", LocalDateTime.now(), null, TipoMovimentoEnum.CREDITO
                    , BigDecimal.ZERO);
        });
    }

    @Test
    public void validaTipoMovimento() {
        assertThrows(ExtratoException.class, () -> {
            new Extrato("1", LocalDateTime.now(), "Teste", null, BigDecimal.ZERO);
        });
    }

    @Test
    public void validaValorNull() {
        assertThrows(ExtratoException.class, () -> {
            new Extrato("1", LocalDateTime.now(), "Teste", null, null);
        });
    }

    @Test
    public void validaValorNegativo() {
        assertThrows(ExtratoException.class, () -> {
            new Extrato("1", LocalDateTime.now(), "Teste", null,
                    BigDecimal.TEN.multiply(BigDecimal.valueOf(-1)));
        });
    }
}