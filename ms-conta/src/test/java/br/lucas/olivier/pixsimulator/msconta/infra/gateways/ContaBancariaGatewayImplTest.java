package br.lucas.olivier.pixsimulator.msconta.infra.gateways;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ContaBancariaGatewayImplTest {

    private ContaBancariaJpaRepository contaBancariaJpaRepository;
    private ContaBancariaGatewayImpl contaBancariaGatewayImpl;

    @BeforeEach
    void setUp() {
        contaBancariaJpaRepository = Mockito.mock(ContaBancariaJpaRepository.class);
        contaBancariaGatewayImpl = new ContaBancariaGatewayImpl(contaBancariaJpaRepository);
    }

    @Test
    void deveRetornarContaBancariaPorId() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        Mockito.when(contaBancariaJpaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ContaBancariaWrapper.toJpaEntity(contaBancaria)));

        Optional<ContaBancaria> result = contaBancariaGatewayImpl.findById("1");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(contaBancaria, result.get());
    }

    @Test
    void deveSalvarContaBancaria() {
        ContaBancaria contaBancaria = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        Mockito.when(contaBancariaJpaRepository.saveAndFlush(Mockito.any())).thenReturn(ContaBancariaWrapper.toJpaEntity(contaBancaria));

        Optional<ContaBancaria> result = contaBancariaGatewayImpl.save(contaBancaria);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(contaBancaria, result.get());
    }

    @Test
    void deveRetornarTodasAsContasBancarias() {
        ContaBancaria contaBancaria1 = new ContaBancaria("1234", "56789", "0", TipoConta.CORRENTE);
        ContaBancaria contaBancaria2 = new ContaBancaria("9876", "54321", "1", TipoConta.CORRENTE);
        List<ContaBancaria> contasBancarias = Arrays.asList(contaBancaria1, contaBancaria2);

        Mockito.when(contaBancariaJpaRepository.findAll()).thenReturn(Arrays.asList(ContaBancariaWrapper.toJpaEntity(contaBancaria1), ContaBancariaWrapper.toJpaEntity(contaBancaria2)));

        List<ContaBancaria> result = contaBancariaGatewayImpl.findAll();

        Assertions.assertEquals(contasBancarias, result);
    }
}