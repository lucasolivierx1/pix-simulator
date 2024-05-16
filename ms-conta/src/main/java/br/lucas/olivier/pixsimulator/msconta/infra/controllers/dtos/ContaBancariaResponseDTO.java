package br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;

import java.util.List;

public record ContaBancariaResponseDTO(String id, String agencia, String numero, String digito, TipoConta tipoConta,
                                       List<ChavePixResponseDTO> chavesPix) {
}
