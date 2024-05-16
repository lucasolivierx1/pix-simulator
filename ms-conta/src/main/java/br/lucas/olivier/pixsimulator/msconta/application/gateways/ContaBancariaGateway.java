package br.lucas.olivier.pixsimulator.msconta.application.gateways;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;

import java.util.List;
import java.util.Optional;

public interface ContaBancariaGateway {

    Optional<ContaBancaria> findById(String id);

    Optional<ContaBancaria> save(ContaBancaria contaBancaria);

    List<ContaBancaria> findAll();

}
