package br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import jakarta.persistence.*;

@Entity
@Table(name = "conta_bancaria")
public record ContaBancariaJpaEntity(
        @Id String id,
        String agencia,
        String numero,
        String digito,
        @Enumerated(EnumType.STRING) TipoConta tipoConta) {
}
