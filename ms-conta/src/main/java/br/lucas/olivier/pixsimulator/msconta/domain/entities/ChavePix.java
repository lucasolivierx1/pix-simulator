package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
public record ChavePix(String chave, TipoChave tipoChave, String contaId) {
    public ChavePix {
        if (tipoChave == null) {
            throw new PixSimulatorException("Tipo de chave não pode ser nulo ou vazio");
        }
        if (chave == null || chave.isBlank() && tipoChave != TipoChave.ALEATORIA) {
            throw new PixSimulatorException("Chave não pode ser nula ou vazia");
        }
        if (tipoChave.equals(TipoChave.CPF) && !chave.matches("^[0-9]{11}$")) {
            throw new PixSimulatorException("CPF inválido");
        }
        if (tipoChave.equals(TipoChave.CNPJ) && !chave.matches("^[0-9]{14}$")) {
            throw new PixSimulatorException("CNPJ inválido");
        }
        if (tipoChave.equals(TipoChave.TELEFONE) && !chave.matches("^\\+[1-9][0-9]\\d{1,14}$")) {
            throw new PixSimulatorException("Telefone inválido");
        }
        if (tipoChave.equals(TipoChave.EMAIL) && !chave.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new PixSimulatorException("Email inválido");
        }
        if (tipoChave.equals(TipoChave.ALEATORIA)) {
            chave = gerarChavePixAleatoria();

            if (!chave.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
                throw new PixSimulatorException("Chave aleatória inválida");
            }
        }
    }

    public static String gerarChavePixAleatoria() {
        log.info("Gerando chave pix aleatória");
        return UUID.randomUUID().toString();
    }
}
