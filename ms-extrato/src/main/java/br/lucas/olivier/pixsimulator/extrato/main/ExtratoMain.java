package br.lucas.olivier.pixsimulator.extrato.main;

import br.lucas.olivier.pixsimulator.extrato.application.gateways.ExtratoGateway;
import br.lucas.olivier.pixsimulator.extrato.application.usecases.GerarExtratoUseCase;
import br.lucas.olivier.pixsimulator.extrato.infra.gateways.ExtratoGatewayImpl;
import br.lucas.olivier.pixsimulator.extrato.infra.persistence.ExtratoMongoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ExtratoMain {

    @Bean
    public ExtratoGateway extratoGateway(ExtratoMongoRepository extratoMongoRepository){
        return new ExtratoGatewayImpl(extratoMongoRepository);
    }

    @Bean
    public GerarExtratoUseCase gerarExtratoUseCase(ExtratoGateway extratoGateway){
        return new GerarExtratoUseCase(extratoGateway);
    }

}
