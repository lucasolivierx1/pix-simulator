package br.lucas.olivier.pixsimulator.msconta.main;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.AdicionarChavePixUseCase;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.BuscaChavesPixUseCase;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.BuscaContasBancariasUseCase;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.CadastrarContaBancariaUseCase;
import br.lucas.olivier.pixsimulator.msconta.infra.gateways.ChavePixGatewayImpl;
import br.lucas.olivier.pixsimulator.msconta.infra.gateways.ContaBancariaGatewayImpl;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix.ChavePixJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaJpaEntity;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsContaMain {

    @Bean
    public ChavePixGateway chavePixGateway(ChavePixJpaRepository chavePixJpaRepository) {
        return new ChavePixGatewayImpl(chavePixJpaRepository);
    }

    @Bean
    public ContaBancariaGateway contaBancariaGateway(ContaBancariaJpaRepository contaBancariaJpaRepository) {
        return new ContaBancariaGatewayImpl(contaBancariaJpaRepository);
    }

    @Bean
    public CadastrarContaBancariaUseCase cadastrarContaBancariaUseCase(ContaBancariaGateway contaBancariaGateway) {
        return new CadastrarContaBancariaUseCase(contaBancariaGateway);
    }

    @Bean
    public AdicionarChavePixUseCase adicionarChavePixUseCase(ChavePixGateway chavePixGateway, ContaBancariaGateway contaBancariaGateway) {
        return new AdicionarChavePixUseCase(chavePixGateway, contaBancariaGateway);
    }

    @Bean
    public BuscaContasBancariasUseCase buscaContasBancariasUseCase(ContaBancariaGateway contaBancariaGateway) {
        return new BuscaContasBancariasUseCase(contaBancariaGateway);
    }

    @Bean
    public BuscaChavesPixUseCase buscaChavesPixUseCase(ChavePixGateway chavePixGateway) {
        return new BuscaChavesPixUseCase(chavePixGateway);
    }

}
