package br.lucas.olivier.pixsimulator.extrato.infra.kafka;

import br.lucas.olivier.pixsimulator.extrato.application.dtos.MovimentacaoContaDTO;
import br.lucas.olivier.pixsimulator.extrato.application.gateways.ExtratoGateway;
import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;
import br.lucas.olivier.pixsimulator.extrato.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PixSimulatorConsumer {

    @Value("${spring.kafka.topics.movimentacao}")
    private String movimentacaoTopic;

    private final ExtratoGateway extratoGateway;

    public PixSimulatorConsumer(ExtratoGateway extratoGateway) {
        this.extratoGateway = extratoGateway;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topics.movimentacao}"
    )
    public void consumeMovimentacaoConta(String message) {
        log.info("Receiving event {} from {}", message, movimentacaoTopic);

        MovimentacaoContaDTO event = JsonUtils.toMovimentacaoConta(message);

        Extrato extrato = Extrato.builder()
                .idConta(event.idConta())
                .descricao(event.descricao())
                .dataHora(event.data())
                .valor(event.valor())
                .tipoMovimento(event.tipoMovimentacao())
                .build();

        extratoGateway.salvarExtrato(extrato);
    }
}
