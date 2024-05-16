package br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;

public class ChavePixWrapper {

    public static ChavePixJpaEntity toJpaEntity(ChavePix chavePix) {
        return new ChavePixJpaEntity(chavePix.chave(), chavePix.tipoChave(), chavePix.contaId());
    }

    public static ChavePix toDomain(ChavePixJpaEntity chavePixJpaEntity) {
        return new ChavePix(chavePixJpaEntity.getChave(), chavePixJpaEntity.getTipoChave(), chavePixJpaEntity.getContaBancariaId());
    }
}
