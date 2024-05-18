package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.dtos.MovimentacaoContaDTO;
import br.lucas.olivier.pixsimulator.msconta.application.enums.TipoMovimentoEnum;
import br.lucas.olivier.pixsimulator.msconta.application.enums.TopicsEnum;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import br.lucas.olivier.pixsimulator.msconta.infra.kafka.PixSimulatorKafkaProducer;
import br.lucas.olivier.pixsimulator.msconta.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferenciaPixUseCase {

    final ChavePixGateway chavePixGateway;
    final ContaBancariaGateway contaBancariaGateway;
    final PixSimulatorKafkaProducer pixSimulatorKafkaProducer;

    public TransferenciaPixUseCase(final ChavePixGateway chavePixGateway,
                                   final ContaBancariaGateway contaBancariaGateway,
                                   final PixSimulatorKafkaProducer pixSimulatorKafkaProducer) {
        this.chavePixGateway = chavePixGateway;
        this.contaBancariaGateway = contaBancariaGateway;
        this.pixSimulatorKafkaProducer = pixSimulatorKafkaProducer;
    }

    public void execute(String idContaorigem, String chavePix, BigDecimal valor) {
        final ChavePix dbChavePix = chavePixGateway.findByChave(chavePix)
                .orElseThrow(() -> new PixSimulatorException("Chave não encontrada"));


        final ContaBancaria origem = contaBancariaGateway.findById(idContaorigem)
                .orElseThrow(() -> new PixSimulatorException("Conta Origem não encontrada"));

        final ContaBancaria destino = contaBancariaGateway.findById(dbChavePix.contaId())
                .orElseThrow(() -> new PixSimulatorException("Conta Destino não encontrada"));


        origem.debitar(valor);
        destino.creditar(valor);

        contaBancariaGateway.save(origem);
        var movimentacaoContaDebitoDTO = new MovimentacaoContaDTO(origem.getId(),
                origem.getAgencia(),
                origem.getNumero(),
                "Transferência PIX",
                LocalDateTime.now(), valor, TipoMovimentoEnum.DEBITO);

        contaBancariaGateway.save(destino);
        var movimentacaoContaCreditoDTO = new MovimentacaoContaDTO(origem.getId(),
                destino.getAgencia(),
                destino.getNumero(),
                "Transferência PIX", LocalDateTime.now(), valor, TipoMovimentoEnum.CREDITO);

        try {
            pixSimulatorKafkaProducer.sendEvent(TopicsEnum.CONTA_MOVIMENTACAO.getTopic(),
                    JsonUtils.toJson(movimentacaoContaDebitoDTO));

            pixSimulatorKafkaProducer.sendEvent(TopicsEnum.CONTA_MOVIMENTACAO.getTopic(),
                    JsonUtils.toJson(movimentacaoContaCreditoDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
