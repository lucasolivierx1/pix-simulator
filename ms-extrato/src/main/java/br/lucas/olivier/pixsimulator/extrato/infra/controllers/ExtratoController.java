package br.lucas.olivier.pixsimulator.extrato.infra.controllers;

import br.lucas.olivier.pixsimulator.extrato.application.dtos.ResumoExtratoDTO;
import br.lucas.olivier.pixsimulator.extrato.application.usecases.GerarExtratoUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extrato")
public class ExtratoController {

    private final GerarExtratoUseCase gerarExtratoUseCase;

    public ExtratoController(GerarExtratoUseCase gerarExtratoUseCase) {
        this.gerarExtratoUseCase = gerarExtratoUseCase;
    }

    @GetMapping("/{idConta}")
    public ResumoExtratoDTO getExtrato(@PathVariable String idConta) {
        return gerarExtratoUseCase.execute(idConta);
    }
}
