package br.lucas.olivier.pixsimulator.extrato.application.gateways;

import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;

import java.util.List;

public interface ExtratoGateway {
    List<Extrato> getExtrato(String idConta);
    void salvarExtrato(Extrato extrato);
}
