package br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conta_bancaria")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaJpaEntity {
    @Id
    String id;
    String agencia;
    String numero;
    String digito;
    @Enumerated(EnumType.STRING)
    TipoConta tipoConta;
    BigDecimal saldo;
}
