package br.lucas.olivier.pixsimulator.extrato.infra.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "extrato")
public class ExtratoDocument {
    @Id private String id;
    private String idConta;
    private String descricao;
    private LocalDateTime data;
    private BigDecimal valor;
    private String tipo;

}
