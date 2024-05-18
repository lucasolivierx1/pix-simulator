package br.lucas.olivier.pixsimulator.extrato.infra.controllers;

import br.lucas.olivier.pixsimulator.extrato.application.dtos.ExtratoDTO;
import br.lucas.olivier.pixsimulator.extrato.application.dtos.ResumoExtratoDTO;
import br.lucas.olivier.pixsimulator.extrato.application.usecases.GerarExtratoUseCase;
import br.lucas.olivier.pixsimulator.extrato.domain.enums.TipoMovimentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExtratoControllerTest {

    @MockBean
    private GerarExtratoUseCase gerarExtratoUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetExtrato() throws Exception {

        List<ExtratoDTO> extratos = new ArrayList<>();
        extratos.add(new ExtratoDTO(TipoMovimentoEnum.CREDITO,
                LocalDateTime.now(),
                "Teste",
                BigDecimal.valueOf(100)));

        extratos.add(new ExtratoDTO(TipoMovimentoEnum.CREDITO,
                LocalDateTime.now(), "Teste 2",
                BigDecimal.valueOf(100)));

        String idConta = "1";

        ResumoExtratoDTO resumoExtratoDTO = new ResumoExtratoDTO(BigDecimal.valueOf(50), extratos);

        when(gerarExtratoUseCase.execute(idConta)).thenReturn(resumoExtratoDTO);

        mockMvc.perform(get("/api/extrato/" + idConta)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.saldoAtual").value(50))
                .andExpect(jsonPath("$.movimentacoes[0].tipoMovimento").value("CREDITO"))
                .andExpect(jsonPath("$.movimentacoes[0].descricao").value("Teste"))
                .andExpect(jsonPath("$.movimentacoes[0].valor").value(BigDecimal.valueOf(100)))
                .andExpect(jsonPath("$.movimentacoes[1].tipoMovimento").value("CREDITO"))
                .andExpect(jsonPath("$.movimentacoes[1].descricao").value("Teste 2"))
                .andExpect(jsonPath("$.movimentacoes[1].valor").value(BigDecimal.valueOf(100)))
                .andExpect(status().isOk());
    }
}
