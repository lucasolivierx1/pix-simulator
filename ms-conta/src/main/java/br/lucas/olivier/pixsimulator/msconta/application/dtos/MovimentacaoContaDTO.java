package br.lucas.olivier.pixsimulator.msconta.application.dtos;

import br.lucas.olivier.pixsimulator.msconta.application.enums.TipoMovimentoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentacaoContaDTO(String idConta,
                                   String agencia,
                                   String contaNumero,
                                   String descricao,
                                   LocalDateTime data,
                                   BigDecimal valor,
                                   TipoMovimentoEnum tipoMovimentacao){}
