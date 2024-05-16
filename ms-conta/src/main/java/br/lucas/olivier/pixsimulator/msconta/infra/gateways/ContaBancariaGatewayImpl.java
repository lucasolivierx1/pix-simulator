package br.lucas.olivier.pixsimulator.msconta.infra.gateways;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaWrapper;

import java.util.List;
import java.util.Optional;

public class ContaBancariaGatewayImpl implements ContaBancariaGateway {

    private final ContaBancariaJpaRepository contaBancariaJpaRepository;

    public ContaBancariaGatewayImpl(ContaBancariaJpaRepository contaBancariaJpaRepository) {
        this.contaBancariaJpaRepository = contaBancariaJpaRepository;
    }

    @Override
    public Optional<ContaBancaria> findById(String id) {
        return contaBancariaJpaRepository.findById(id)
                .map(ContaBancariaWrapper::toDomain);
    }

    @Override
    public Optional<ContaBancaria> save(ContaBancaria contaBancaria) {
        return Optional.of(contaBancariaJpaRepository.saveAndFlush(ContaBancariaWrapper.toJpaEntity(contaBancaria)))
                .map(ContaBancariaWrapper::toDomain);
    }

    @Override
    public List<ContaBancaria> findAll() {
        return contaBancariaJpaRepository.findAll().stream().map(ContaBancariaWrapper::toDomain).toList();
    }
}
