package br.lucas.olivier.pixsimulator.extrato.infra.gateways;

import br.lucas.olivier.pixsimulator.extrato.domain.entities.Extrato;
import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;
import br.lucas.olivier.pixsimulator.extrato.infra.persistence.ExtratoDocument;
import br.lucas.olivier.pixsimulator.extrato.infra.persistence.ExtratoMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ExtratoGatewayImplTest {


    private ExtratoMongoRepository extratoMongoRepository;
    private ExtratoGatewayImpl extratoGatewayImpl;

    @BeforeEach
    public void setup() {
        extratoMongoRepository = Mockito.mock(ExtratoMongoRepository.class);
        extratoGatewayImpl = new ExtratoGatewayImpl(extratoMongoRepository);
    }

    @Test
    public void testGetExtrato() {
        String idConta = "1";
        ExtratoDocument extratoDocument1 = new ExtratoDocument("1", idConta, "Teste",
                LocalDateTime.now(), BigDecimal.valueOf(100), TipoMovimentoEnum.CREDITO.name());

        ExtratoDocument extratoDocument2 = new ExtratoDocument("2", idConta, "Teste",
                LocalDateTime.now(), BigDecimal.valueOf(50), TipoMovimentoEnum.DEBITO.name());
        List<ExtratoDocument> extratoDocuments = Arrays.asList(extratoDocument1, extratoDocument2);

        when(extratoMongoRepository.findAllByIdConta(idConta)).thenReturn(extratoDocuments);

        List<Extrato> extratos = extratoGatewayImpl.getExtrato(idConta);

        assertEquals(2, extratos.size());
        assertEquals(idConta, extratos.get(0).getIdConta());
        assertEquals(idConta, extratos.get(1).getIdConta());
    }

    @Test
    public void testSalvarExtrato() {
        Extrato extrato = new Extrato("1", LocalDateTime.now(), "Teste", TipoMovimentoEnum.CREDITO, BigDecimal.valueOf(100));

        extratoGatewayImpl.salvarExtrato(extrato);

        Mockito.verify(extratoMongoRepository,
                Mockito.times(1)).save(Mockito.any(ExtratoDocument.class));
    }
}
