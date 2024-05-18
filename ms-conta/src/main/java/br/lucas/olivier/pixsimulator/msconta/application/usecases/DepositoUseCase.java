package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.dtos.MovimentacaoContaDTO;
import br.lucas.olivier.pixsimulator.msconta.application.enums.TipoMovimentoEnum;
import br.lucas.olivier.pixsimulator.msconta.application.enums.TopicsEnum;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import br.lucas.olivier.pixsimulator.msconta.infra.kafka.PixSimulatorKafkaProducer;
import br.lucas.olivier.pixsimulator.msconta.utils.JsonUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositoUseCase {
    private final ContaBancariaGateway contaBancariaGateway;
    private final PixSimulatorKafkaProducer pixSimulatorKafkaProducer;

    public DepositoUseCase(ContaBancariaGateway contaBancariaGateway, PixSimulatorKafkaProducer pixSimulatorKafkaProducer) {
        this.contaBancariaGateway = contaBancariaGateway;
        this.pixSimulatorKafkaProducer = pixSimulatorKafkaProducer;
    }

    public void execute(String idConta, BigDecimal valor) {

        var contaBancaria = contaBancariaGateway.findById(idConta)
                .orElseThrow(() -> new PixSimulatorException("Conta não encontrada"));

        contaBancaria.creditar(valor);

        contaBancariaGateway.save(contaBancaria);

        var event = new MovimentacaoContaDTO(idConta,
                contaBancaria.getAgencia(),
                contaBancaria.getNumero()
                ,"Deposito",
                LocalDateTime.now(),
                valor,
                TipoMovimentoEnum.CREDITO);

        pixSimulatorKafkaProducer.sendEvent(TopicsEnum.CONTA_MOVIMENTACAO.getTopic(), JsonUtils.toJson(event));

    }
}
