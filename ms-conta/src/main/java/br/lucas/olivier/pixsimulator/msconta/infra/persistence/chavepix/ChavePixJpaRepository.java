package br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChavePixJpaRepository extends JpaRepository<ChavePixJpaEntity, String> {
    @Query("SELECT c FROM ChavePixJpaEntity c WHERE c.chave = :chave")
    Optional<ChavePix> findByChave(String chave);

    @Query("SELECT c FROM ChavePixJpaEntity c WHERE c.contaBancariaId = :contaId")
    List<ChavePix> findAllByContaBancariaId(String contaId);
}
