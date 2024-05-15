package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContaBancariaTest {

    @Test
    void deveCriarContaBancariaValida() {
        Assertions.assertDoesNotThrow(() -> {
            ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        });
    }

    @Test
    void deveAdicionarChavePixValida() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF);

        Assertions.assertDoesNotThrow(() -> {
            contaBancaria.addChavePix(chavePix);
        });
    }

    @Test
    void naoDeveAdicionar2OuMaisChavesPixCPF() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF);
        ChavePix chavePix2 = new ChavePix("98732111321", TipoChave.CPF);

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            contaBancaria.addChavePix(chavePix);
            contaBancaria.addChavePix(chavePix2);
        });
    }

    @Test
    void naoDeveAdicionarChavePixNula() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);

        Assertions.assertThrows(NullPointerException.class, () -> {
            contaBancaria.addChavePix(null);
        });
    }

    @Test
    void deveRemoverChavePixValida() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF);
        contaBancaria.addChavePix(chavePix);

        Assertions.assertDoesNotThrow(() -> {
            contaBancaria.removeChavePix(chavePix);
        });
    }

    @Test
    void naoDeveRemoverChavePixInexistente() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF);

        contaBancaria.removeChavePix(chavePix);

        Assertions.assertTrue(contaBancaria.getChavesPix().isEmpty());

    }
}
