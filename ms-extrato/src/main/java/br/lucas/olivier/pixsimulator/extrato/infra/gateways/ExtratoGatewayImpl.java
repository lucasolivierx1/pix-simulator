package br.lucas.olivier.pixsimulator.extrato.infra.gateways;

import br.lucas.olivier.pixsimulator.extrato.application.gateways.ExtratoGateway;
import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;
import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;
import br.lucas.olivier.pixsimulator.extrato.infra.persistence.ExtratoDocument;
import br.lucas.olivier.pixsimulator.extrato.infra.persistence.ExtratoMongoRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExtratoGatewayImpl implements ExtratoGateway {

    final ExtratoMongoRepository extratoMongoRepository;

    public ExtratoGatewayImpl(ExtratoMongoRepository extratoMongoRepository) {
        this.extratoMongoRepository = extratoMongoRepository;
    }

    @Override
    public List<Extrato> getExtrato(String idConta) {
        return extratoMongoRepository.findAllByIdConta(idConta)
                .stream().map(extratoDocument -> {
                    return Extrato.builder()
                            .id(extratoDocument.getId())
                            .idConta(extratoDocument.getIdConta())
                            .descricao(extratoDocument.getDescricao())
                            .dataHora(extratoDocument.getData())
                            .valor(extratoDocument.getValor())
                            .tipoMovimento(TipoMovimentoEnum.valueOf(extratoDocument.getTipo()))
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    public void salvarExtrato(Extrato extrato) {
        ExtratoDocument extratoDocument = new ExtratoDocument();
        extratoDocument.setData(extrato.getDataHora());
        extratoDocument.setDescricao(extrato.getDescricao());
        extratoDocument.setIdConta(extrato.getIdConta());
        extratoDocument.setTipo(extrato.getTipoMovimento().name());
        extratoDocument.setValor(extrato.getValor());
        extratoDocument.setId(UUID.randomUUID().toString());

        extratoMongoRepository.save(extratoDocument);
    }
}
