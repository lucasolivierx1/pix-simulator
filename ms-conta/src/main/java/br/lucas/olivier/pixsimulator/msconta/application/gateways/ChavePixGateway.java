package br.lucas.olivier.pixsimulator.msconta.application.gateways;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;

import java.util.List;
import java.util.Optional;

public interface ChavePixGateway {
    Optional<ChavePix> findByChave(String chave);

    List<ChavePix> findAllByContaBancariaId(String contaId);

    Optional<ChavePix> save(ChavePix chavePix);

}
