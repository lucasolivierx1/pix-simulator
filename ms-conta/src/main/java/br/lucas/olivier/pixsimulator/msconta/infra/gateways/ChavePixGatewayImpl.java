package br.lucas.olivier.pixsimulator.msconta.infra.gateways;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix.ChavePixJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix.ChavePixWrapper;

import java.util.List;
import java.util.Optional;

public class ChavePixGatewayImpl implements ChavePixGateway {

    private final ChavePixJpaRepository chavePixJpaRepository;

    public ChavePixGatewayImpl(ChavePixJpaRepository chavePixJpaRepository) {
        this.chavePixJpaRepository = chavePixJpaRepository;
    }

    @Override
    public Optional<ChavePix> findByChave(String chave) {
        return chavePixJpaRepository.findByChave(chave);
    }

    @Override
    public List<ChavePix> findAllByContaBancariaId(String contaId) {
        return chavePixJpaRepository.findAllByContaBancariaId(contaId);
    }

    @Override
    public Optional<ChavePix> save(ChavePix chavePix) {
        return Optional.of(chavePixJpaRepository.saveAndFlush(ChavePixWrapper.toJpaEntity(chavePix)))
                .map(ChavePixWrapper::toDomain);
    }

    @Override
    public void delete(ChavePix chavePix) {
        chavePixJpaRepository.delete(ChavePixWrapper.toJpaEntity(chavePix));
    }
}
