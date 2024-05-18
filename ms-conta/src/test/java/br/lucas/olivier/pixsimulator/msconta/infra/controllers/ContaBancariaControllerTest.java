package br.lucas.olivier.pixsimulator.msconta.infra.controllers;

import br.lucas.olivier.pixsimulator.msconta.domain.entities.ContaBancaria;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoChave;
import br.lucas.olivier.pixsimulator.msconta.domain.enums.TipoConta;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ChavePixRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaResponseDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.DepositoRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper.ContaBancariaDTOWrapper;
import br.lucas.olivier.pixsimulator.msconta.infra.persistence.contabancaria.ContaBancariaWrapper;
import br.lucas.olivier.pixsimulator.msconta.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContaBancariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static ContaBancariaResponseDTO contaBancariaResponseDTO;

    @Test
    @Order(1)
    void deveCriarNovaContaBancaria() throws Exception {
        ContaBancariaRequestDTO request = new ContaBancariaRequestDTO("1234", "98765", "2", TipoConta.CORRENTE);
        MvcResult result = mockMvc.perform(post("/api/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agencia").value(request.agencia()))
                .andExpect(jsonPath("$.numero").value(request.numero()))
                .andExpect(jsonPath("$.digito").value(request.digito()))
                .andExpect(jsonPath("$.tipoConta").value(request.tipoConta().name()))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        contaBancariaResponseDTO = objectMapper.readValue(response, ContaBancariaResponseDTO.class);

    }

    @Test
    @Order(2)
    void deveRetornarTodasAsContasBancarias() throws Exception {
        var contaBancaria = new ContaBancaria("1234", "98765", "2", TipoConta.CORRENTE);

        mockMvc.perform(get("/api/conta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contaBancariaResponseDTO.id()))
                .andExpect(jsonPath("$[0].agencia").value(contaBancariaResponseDTO.agencia()))
                .andExpect(jsonPath("$[0].numero").value(contaBancariaResponseDTO.numero()))
                .andExpect(jsonPath("$[0].digito").value(contaBancariaResponseDTO.digito()))
                .andExpect(jsonPath("$[0].tipoConta").value(contaBancariaResponseDTO.tipoConta().name()));
    }


    @Test
    @Order(3)
    void deveAdicionarChavePix() throws Exception {
        ChavePixRequestDTO request = new ChavePixRequestDTO(TipoChave.CPF, "12345678910");

        mockMvc.perform(put(String.format("/api/conta/%s/add-chave-pix",  contaBancariaResponseDTO.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/conta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contaBancariaResponseDTO.id()))
                .andExpect(jsonPath("$[0].agencia").value(contaBancariaResponseDTO.agencia()))
                .andExpect(jsonPath("$[0].numero").value(contaBancariaResponseDTO.numero()))
                .andExpect(jsonPath("$[0].digito").value(contaBancariaResponseDTO.digito()))
                .andExpect(jsonPath("$[0].tipoConta").value(contaBancariaResponseDTO.tipoConta().name()))
                .andExpect(jsonPath("$[0].chavesPix[0].chave").value(request.chave()));
    }

    @Test
    @Order(4)
    void deveDepositarNaConta() throws Exception {

        DepositoRequestDTO request = new DepositoRequestDTO(new BigDecimal(100));

        mockMvc.perform(put(String.format("/api/conta/%s/deposito", contaBancariaResponseDTO.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/conta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contaBancariaResponseDTO.id()))
                .andExpect(jsonPath("$[0].agencia").value(contaBancariaResponseDTO.agencia()))
                .andExpect(jsonPath("$[0].numero").value(contaBancariaResponseDTO.numero()))
                .andExpect(jsonPath("$[0].digito").value(contaBancariaResponseDTO.digito()))
                .andExpect(jsonPath("$[0].tipoConta").value(contaBancariaResponseDTO.tipoConta().name()));
    }


}
