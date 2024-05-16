package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;

import java.util.List;

public class BuscaChavesPixUseCase {
    private final ChavePixGateway chavePixGateway;

    public BuscaChavesPixUseCase(ChavePixGateway chavePixGateway) {
        this.chavePixGateway = chavePixGateway;
    }

    public List<ChavePix> execute(String idConta) {
        return chavePixGateway.findAllByContaBancariaId(idConta);
    }
}
