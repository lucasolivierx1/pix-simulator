package br.lucas.olivier.pixsimulator.extrato.application.dtos;

import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentacaoContaDTO(String idConta,
                                   String agencia,
                                   String contaNumero,
                                   String descricao,
                                   LocalDateTime data,
                                   BigDecimal valor,
                                   TipoMovimentoEnum tipoMovimentacao){}
