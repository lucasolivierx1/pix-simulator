package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;

import java.util.List;

public class BuscaContasBancariasUseCase {

    private final ContaBancariaGateway contaBancariaGateway;

    public BuscaContasBancariasUseCase(ContaBancariaGateway contaBancariaGateway) {
        this.contaBancariaGateway = contaBancariaGateway;
    }

    public List<ContaBancaria> execute() {
        return contaBancariaGateway.findAll();
    }
}
