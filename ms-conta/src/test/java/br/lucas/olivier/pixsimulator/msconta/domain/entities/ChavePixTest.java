package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ChavePixTest {
    @Test
    void deveCriarChavePixValida() {
        Assertions.assertDoesNotThrow(() -> {
            ChavePix cpf = new ChavePix("12345678909", TipoChave.CPF);
            ChavePix cnpj = new ChavePix("12345678901234", TipoChave.CNPJ);
            ChavePix telefone = new ChavePix("+5511999999999", TipoChave.TELEFONE);
            ChavePix email = new ChavePix("test@test.com.br", TipoChave.EMAIL);
            ChavePix aleatoria = new ChavePix("123e4567-e89b-12d3-a456-426614174000", TipoChave.ALEATORIA);
        });
    }

    @Test
    void deveLancarExcecaoParaChaveNulaOuVazia() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.CPF);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.CPF);
        });

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.CNPJ);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.CNPJ);
        });

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.TELEFONE);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.TELEFONE);
        });

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix(null, TipoChave.EMAIL);
        });
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("", TipoChave.EMAIL);
        });
    }

    @Test
    void deveLancarExcecaoParaTipoChaveNulo() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("12345678909", null);
        });
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("1234567890", TipoChave.CPF);
        });
    }

    @Test
    void deveLancarExcecaoParaCnpjInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("1234567890123", TipoChave.CNPJ);
        });
    }

    @Test
    void deveLancarExcecaoParaTelefoneInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("11999999999", TipoChave.TELEFONE);
        });
    }

    @Test
    void deveLancarExcecaoParaEmailInvalido() {
        Assertions.assertThrows(PixSimulatorException.class, () -> {
            ChavePix chavePix = new ChavePix("teste@teste", TipoChave.EMAIL);
        });
    }

    @Test
    void deveLancarExcecaoParaChaveAleatoriaInvalida() {
        String regex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
        Assertions.assertDoesNotThrow(() -> {
            ChavePix chavePix = new ChavePix("", TipoChave.ALEATORIA);

            Assertions.assertTrue(Objects.nonNull(chavePix.chave()), "Deve ter uma chave");
            Assertions.assertTrue(chavePix.chave().matches(regex), "Deve ter o formato de UUID");
        });
    }
}