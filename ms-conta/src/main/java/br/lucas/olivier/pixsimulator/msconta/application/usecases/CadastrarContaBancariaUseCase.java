package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CadastrarContaBancariaUseCase {
    private final ContaBancariaGateway contaBancariaGateway;

    public CadastrarContaBancariaUseCase(final ContaBancariaGateway contaBancariaGateway) {
        this.contaBancariaGateway = contaBancariaGateway;
    }

    public ContaBancaria execute(String agencia, String numero, String digito, TipoConta tipoConta) {
        log.info("Cadastrando conta bancária: {}-{}", agencia, numero);

        var contaBancaria = new ContaBancaria(agencia, numero, digito, tipoConta);

        var contaBancariaDb = contaBancariaGateway.save(contaBancaria)
                .orElseThrow(() -> new PixSimulatorException("Erro ao cadastrar conta bancária"));

        log.info("Conta bancária cadastrada com sucesso: {}", contaBancaria.getId());

        return contaBancariaDb;
    }
}
