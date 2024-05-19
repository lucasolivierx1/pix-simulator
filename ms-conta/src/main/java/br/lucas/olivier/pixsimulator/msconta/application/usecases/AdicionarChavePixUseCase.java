package br.lucas.olivier.pixsimulator.msconta.application.usecases;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.domain.entities.ChavePix;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.Optional;

@Log4j2
public class AdicionarChavePixUseCase {
    private final ChavePixGateway chavePixGateway;
    private final ContaBancariaGateway contaBancariaGateway;

    public AdicionarChavePixUseCase(final ChavePixGateway chavePixGateway, final ContaBancariaGateway contaBancariaGateway) {
        this.chavePixGateway = chavePixGateway;
        this.contaBancariaGateway = contaBancariaGateway;
    }

    public void execute(String contaId, String chave, TipoChave tipoChave) {
        log.info("Adicionando chave pix: {} para a conta: {}", chave, contaId);

        chavePixGateway.findByChave(chave).ifPresent((s) -> {
            log.info("Chave já cadastrada ");
            throw new PixSimulatorException("Chave já cadastrada por favor tente outro valor");
        });

        var conta = contaBancariaGateway.findById(contaId)
                .orElseThrow(() -> new PixSimulatorException("Conta não encontrada"));

        var chavePix = new ChavePix(chave, tipoChave, contaId);


        var chavePixDb = chavePixGateway.save(chavePix)
                .orElseThrow(() -> new PixSimulatorException("Erro ao salvar chave pix"));

        conta.addChavePix(chavePixDb);

        contaBancariaGateway.save(conta);
    }

}
