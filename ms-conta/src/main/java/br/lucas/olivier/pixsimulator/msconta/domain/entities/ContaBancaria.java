package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ContaBancaria {

    private final String agencia;
    private final String numero;
    private final String digito;
    private final TipoConta tipoConta;

    public ContaBancaria(String agencia, String numero, String digito, TipoConta tipoConta) {
        this.agencia = agencia;
        this.numero = numero;
        this.digito = digito;
        this.tipoConta = tipoConta;
        this.chavesPix = new ArrayList<>();
        this.validate();
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
    }

}
