package br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(String idContaOrigem, String chavePix, BigDecimal valor) {
}
