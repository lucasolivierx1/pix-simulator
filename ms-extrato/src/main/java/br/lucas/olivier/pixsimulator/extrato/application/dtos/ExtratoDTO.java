package br.lucas.olivier.pixsimulator.extrato.application.dtos;

import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExtratoDTO(TipoMovimentoEnum tipoMovimento,
                         LocalDateTime dataHora,
                         String descricao,
                         BigDecimal valor) {
}
