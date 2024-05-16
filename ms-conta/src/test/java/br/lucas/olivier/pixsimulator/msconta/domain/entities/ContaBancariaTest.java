package br.lucas.olivier.pixsimulator.msconta.domain.entities;

import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF, "1");

        Assertions.assertDoesNotThrow(() -> {
            contaBancaria.addChavePix(chavePix);
        });
    }

    @Test
    void naoDeveAdicionar2OuMaisChavesPixCPF() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF,"1");
        ChavePix chavePix2 = new ChavePix("98732111321", TipoChave.CPF,"1");

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
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF, "1");
        contaBancaria.addChavePix(chavePix);

        Assertions.assertDoesNotThrow(() -> {
            contaBancaria.removeChavePix(chavePix);
        });
    }

    @Test
    void naoDeveRemoverChavePixInexistente() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ChavePix chavePix = new ChavePix("12345678909", TipoChave.CPF,"1");

        contaBancaria.removeChavePix(chavePix);

        Assertions.assertTrue(contaBancaria.getChavesPix().isEmpty());
    }

    @Test
    void deveCreditarSaldo() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        contaBancaria.creditar(BigDecimal.TEN);

        Assertions.assertEquals(BigDecimal.TEN, contaBancaria.getSaldo());
    }

    @Test
    void deveDebitarSaldo() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        contaBancaria.creditar(BigDecimal.TEN);
        contaBancaria.debitar(BigDecimal.ONE);

        Assertions.assertEquals(BigDecimal.valueOf(9), contaBancaria.getSaldo());
    }

    @Test
    void naoDeveDebitarSaldoNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        contaBancaria.creditar(BigDecimal.TEN);

        Assertions.assertThrows(PixSimulatorException.class, () -> {
            contaBancaria.debitar(BigDecimal.valueOf(11));
        });
    }
}
