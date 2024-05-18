package br.lucas.olivier.pixsimulator.extrato.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public record ResumoExtratoDTO(BigDecimal saldoAtual, List<ExtratoDTO> movimentacoes) {
}
