package br.lucas.olivier.pixsimulator.extrato.utils;

import br.lucas.olivier.pixsimulator.extrato.application.dtos.MovimentacaoContaDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error to convert object to json", e);
            return "";
        }
    }

    public static MovimentacaoContaDTO toMovimentacaoConta(String message) {
        try {
            return objectMapper.readValue(message, MovimentacaoContaDTO.class);
        } catch (Exception e) {
            log.error("Error to convert object to json", e);
            return null;
        }
    }
}
