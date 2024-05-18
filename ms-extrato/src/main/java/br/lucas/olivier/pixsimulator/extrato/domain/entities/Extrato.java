package br.lucas.olivier.pixsimulator.extrato.domain.entities;

import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;
import br.lucas.olivier.pixsimulator.extrato.domain.exceptions.ExtratoException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Log4j2
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Extrato {
    String id;
    String idConta;
    LocalDateTime dataHora;
    String descricao;
    TipoMovimentoEnum tipoMovimento;
    BigDecimal valor;

    public Extrato(String idConta,
                   LocalDateTime dataHora,
                   String descricao,
                   TipoMovimentoEnum tipoMovimento,
                   BigDecimal valor) {
        this.idConta = idConta;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.tipoMovimento = tipoMovimento;
        this.valor = valor;
        this.validate();
    }

    private void validate() {
        if (this.idConta == null || this.idConta.isEmpty()) {
            log.error("Id da conta não pode ser nulo ou vazio");
            throw new ExtratoException("Id da conta não pode ser nulo ou vazio");
        }
        if (this.dataHora == null) {
            log.error("Data e hora não podem ser nulas");
            throw new ExtratoException("Data e hora não podem ser nulas");
        }
        if (this.descricao == null || this.descricao.isEmpty()) {
            log.error("Descrição não pode ser nula ou vazia");
            throw new ExtratoException("Descrição não pode ser nula ou vazia");
        }
        if (this.tipoMovimento == null) {
            log.error("Tipo de movimento não pode ser nulo");
            throw new ExtratoException("Tipo de movimento não pode ser nulo");
        }

        if (this.valor == null || this.valor.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Valor não pode ser nulo ou menor ou igual a zero");
            throw new ExtratoException("Valor não pode ser nulo ou menor ou igual a zero");
        }

    }

}
