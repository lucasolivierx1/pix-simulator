package br.lucas.olivier.pixsimulator.extrato.application.usecases;

import br.lucas.olivier.pixsimulator.extrato.application.dtos.ExtratoDTO;
import br.lucas.olivier.pixsimulator.extrato.application.dtos.ResumoExtratoDTO;
import br.lucas.olivier.pixsimulator.extrato.application.gateways.ExtratoGateway;
import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;
import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;

import java.math.BigDecimal;
import java.util.List;

public class GerarExtratoUseCase {
    private final ExtratoGateway extratoGateway;

    public GerarExtratoUseCase(ExtratoGateway extratoGateway) {
        this.extratoGateway = extratoGateway;
    }

    public ResumoExtratoDTO execute(String idConta) {
        BigDecimal saldoConta = BigDecimal.ZERO;

        List<Extrato> extratos = extratoGateway.getExtrato(idConta);

        BigDecimal debito = extratos.stream()
                .filter(extrato -> extrato.getTipoMovimento().equals(TipoMovimentoEnum.DEBITO))
                .map(Extrato::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal credito = extratos.stream()
                .filter(extrato -> extrato.getTipoMovimento().equals(TipoMovimentoEnum.CREDITO))
                .map(Extrato::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        saldoConta = credito.subtract(debito);

        List<ExtratoDTO> extratoDTOList = extratos.stream()
                .map(extrato -> new ExtratoDTO(extrato.getTipoMovimento(), extrato.getDataHora(), extrato.getDescricao(), extrato.getValor()))
                .toList();
        return new ResumoExtratoDTO(saldoConta, extratoDTOList);
    }
}
