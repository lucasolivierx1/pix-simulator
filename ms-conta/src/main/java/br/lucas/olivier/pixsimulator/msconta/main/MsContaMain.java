package br.lucas.olivier.pixsimulator.msconta.main;

import br.lucas.olivier.pixsimulator.msconta.application.gateways.ChavePixGateway;
import br.lucas.olivier.pixsimulator.msconta.application.gateways.ContaBancariaGateway;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.*;
import br.lucas.olivier.pixsimulator.msconta.infra.gateways.ChavePixGatewayImpl;
import br.lucas.olivier.pixsimulator.msconta.infra.gateways.ContaBancariaGatewayImpl;
import br.lucas.olivier.pixsimulator.msconta.infra.kafka.PixSimulatorKafkaProducer;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.chavepix.ChavePixJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaJpaEntity;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaJpaRepository;
import br.lucas.olivier.pixsimulator.msconta.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
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

    @Bean
    public TransferenciaPixUseCase transferenciaPixUseCase(ChavePixGateway chavePixGateway,
                                                           ContaBancariaGateway contaBancariaGateway,
                                                           PixSimulatorKafkaProducer kafkaProducer) {
        return new TransferenciaPixUseCase(chavePixGateway, contaBancariaGateway, kafkaProducer);
    }

}
