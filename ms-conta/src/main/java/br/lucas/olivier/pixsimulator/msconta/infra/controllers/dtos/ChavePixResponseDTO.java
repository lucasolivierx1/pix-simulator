package br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;

public record ChavePixResponseDTO(String chave, TipoChave tipoChave){}
