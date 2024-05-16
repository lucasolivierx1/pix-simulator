package br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ChavePixResponseDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaResponseDTO;

import java.util.List;

public class ContaBancariaDTOWrapper {

    public static ContaBancariaResponseDTO toResponseDTO(ContaBancaria contaBancaria, List<ChavePixResponseDTO> chavesPix) {
        return new ContaBancariaResponseDTO(contaBancaria.getId(),
                contaBancaria.getAgencia(),
                contaBancaria.getNumero(),
                contaBancaria.getDigito(),
                contaBancaria.getTipoConta(),
                chavesPix);
    }
}
