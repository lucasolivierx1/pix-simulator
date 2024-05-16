package br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaBancariaJpaRepository extends JpaRepository<ContaBancariaJpaEntity, String> {
}
