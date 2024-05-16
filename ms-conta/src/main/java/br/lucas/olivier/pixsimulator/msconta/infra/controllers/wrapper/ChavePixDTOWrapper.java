package br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ChavePixResponseDTO;

public class ChavePixDTOWrapper {
    public static ChavePixResponseDTO toResponseDTO(ChavePix chavePix) {
        return new ChavePixResponseDTO(chavePix.chave(), chavePix.tipoChave());
    }
}
