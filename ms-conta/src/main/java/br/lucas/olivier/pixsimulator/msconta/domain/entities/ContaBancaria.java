package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import br.lucas.olivier.pixsimulator.msconta.utils.GenerateIdUtils;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@Data
public class ContaBancaria {

    private final String id;
    private final String agencia;
    private final String numero;
    private final String digito;
    private final TipoConta tipoConta;
    private BigDecimal saldo = BigDecimal.ZERO;

    public ContaBancaria(String id, String agencia, String numero, String digito, TipoConta tipoConta) {
        this.id = id;
        this.agencia = agencia;
        this.numero = numero;
        this.digito = digito;
        this.tipoConta = tipoConta;
        this.chavesPix = new ArrayList<>();
        this.validate();
    }

    public ContaBancaria(String agencia, String numero, String digito, TipoConta tipoConta) {
        this(GenerateIdUtils.generateUUID(), agencia, numero, digito, tipoConta);
    }

    List<ChavePix> chavesPix;

    public void addChavePix(ChavePix chavePix) {
        chavesPix.add(chavePix);
        validate();
    }

    public void removeChavePix(ChavePix chavePix) {
        chavesPix.remove(chavePix);
        validate();
    }

    private void validate() {
        if (agencia == null || agencia.isEmpty()) {
            throw new PixSimulatorException("Agência não pode ser nula ou vazia");
        }
        if ((numero == null || numero.isEmpty())) {
            throw new PixSimulatorException("Número não pode ser nulo ou vazio");
        }
        if (digito == null || digito.isEmpty()) {
            throw new PixSimulatorException("Dígito não pode ser nulo ou vazio");
        }
        if (tipoConta == null) {
            throw new PixSimulatorException("Tipo de conta não pode ser nulo");
        }

        if (chavesPix.stream()
                .filter(chavePix ->
                        chavePix.tipoChave()
                                .equals(TipoChave.CPF)).count() > 1) {
            throw new PixSimulatorException("Conta não pode ter mais de uma chave do tipo CPF");
        }

        if (chavesPix.stream()
                .filter(chavePix ->
                        chavePix.tipoChave()
                                .equals(TipoChave.CNPJ)).count() > 1) {
            throw new PixSimulatorException("Conta não pode ter mais de uma chave do tipo CNPJ");
        }

        if (this.saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new PixSimulatorException("Saldo não pode ser negativo");
        }
    }

    public void debitar(BigDecimal valor) {
        log.info("Debitando valor de {} da conta {}", valor, this);
        validaValor(valor);

        if (saldo.compareTo(valor) < 0) {
            throw new PixSimulatorException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        log.info("Creditando valor de {} na conta {}", valor, this);
        validaValor(valor);
        this.saldo = this.saldo.add(valor);
    }

    private void validaValor(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PixSimulatorException("Valor inválido");
        }
    }

}
