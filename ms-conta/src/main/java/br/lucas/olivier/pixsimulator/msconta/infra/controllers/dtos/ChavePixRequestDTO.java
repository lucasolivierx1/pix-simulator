package br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;

public record ChavePixRequestDTO(TipoChave tipoChave, String chave) {
}
