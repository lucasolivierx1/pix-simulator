package br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;

public record ChavePixRequestDTO(String idConta, TipoChave tipoChave, String chave) {
}
