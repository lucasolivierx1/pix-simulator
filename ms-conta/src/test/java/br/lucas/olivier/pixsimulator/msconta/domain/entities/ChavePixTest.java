package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ChavePixTest {

    private static final String CONTA_ID = "123456";

    @Test
    void deveCriarChavePixValida() {
        Assertions.assertDoesNotThrow(() -> {
            ChavePix cpf = new ChavePix("12345678909", TipoChave.CPF, CONTA_ID);
            ChavePix cnpj = new ChavePix("12345678901234", TipoChave.CNPJ, CONTA_ID);
            ChavePix telefone = new ChavePix("+5511999999999", TipoChave.TELEFONE, CONTA_ID);
            ChavePix email = new ChavePix("test@test.com.br", TipoChave.EMAIL, CONTA_ID);
            ChavePix aleatoria = new ChavePix("123e4567-e89b-12d3-a456-426614174000", TipoChave.ALEATORIA, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaChaveNulaOuVazia() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.CPF, CONTA_ID);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.CPF, CONTA_ID);
        });

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.CNPJ, CONTA_ID);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.CNPJ, CONTA_ID);
        });

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.TELEFONE, CONTA_ID);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.TELEFONE, CONTA_ID);
        });

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.EMAIL, CONTA_ID);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.EMAIL, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaTipoChaveNulo() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("12345678909", null, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("1234567890", TipoChave.CPF, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaCnpjInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("1234567890123", TipoChave.CNPJ, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaTelefoneInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("11999999999", TipoChave.TELEFONE, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaEmailInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("teste@teste", TipoChave.EMAIL, CONTA_ID);
        });
    }

    @Test
    void deveLancarExcecaoParaChaveAleatoriaInvalida() {
        String regex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
        Assertions.assertDoesNotThrow(() -> {
            ChavePix chavePix = new ChavePix("", TipoChave.ALEATORIA, CONTA_ID);

            Assertions.assertTrue(Objects.nonNull(chavePix.chave()), "Deve ter uma chave");
            Assertions.assertTrue(chavePix.chave().matches(regex), "Deve ter o formato de UUID");
        });
    }
}