package br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;

public class ContaBancariaWrapper {

    public static ContaBancariaJpaEntity toJpaEntity(
            ContaBancaria contaBancaria) {
        return new ContaBancariaJpaEntity(contaBancaria.getId(), contaBancaria.getAgencia(), contaBancaria.getNumero(), contaBancaria.getDigito(), contaBancaria.getTipoConta());
    }

    public static ContaBancaria toDomain(
            ContaBancariaJpaEntity contaBancariaJpaEntity) {
        return new ContaBancaria(contaBancariaJpaEntity.getId(), contaBancariaJpaEntity.getAgencia(), contaBancariaJpaEntity.getNumero(), contaBancariaJpaEntity.getDigito(), contaBancariaJpaEntity.getTipoConta());
    }
}
