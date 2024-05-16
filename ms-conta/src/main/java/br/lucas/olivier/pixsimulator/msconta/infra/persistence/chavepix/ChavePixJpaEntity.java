package br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chave_pix")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ChavePixJpaEntity {
    @Id
    @Column(name = "chave")
    String chave;
    @Column(name = "tipo_chave")
    @Enumerated(EnumType.STRING)
    TipoChave tipoChave;

    @Column(name = "conta_bancaria_id")
    String contaBancariaId;

}
