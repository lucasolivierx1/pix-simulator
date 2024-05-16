package br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;

import java.util.List;

public record ContaBancariaRequestDTO(String agencia, String numero, String digito, TipoConta tipoConta) {
}
