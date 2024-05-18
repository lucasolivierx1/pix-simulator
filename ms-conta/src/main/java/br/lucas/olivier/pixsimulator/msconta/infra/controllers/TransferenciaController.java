package br.lucas.olivier.pixsimulator.msconta.infra.controllers;


import br.lucas.olivier.pixsimulator.msconta.application.usecases.TransferenciaPixUseCase;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.TransferenciaRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transferencia")
@Log4j2
public class TransferenciaController {

    private final TransferenciaPixUseCase transferenciaPixUseCase;

    public TransferenciaController(TransferenciaPixUseCase transferenciaPixUseCase) {
        this.transferenciaPixUseCase = transferenciaPixUseCase;
    }

    @PostMapping
    public void transferir(@RequestBody TransferenciaRequestDTO request) {
        log.info("POST > /api/transferencia > Transferindo valor de {} para {}", request.valor(), request.chavePix());
        transferenciaPixUseCase.execute(request.idContaOrigem(), request.chavePix(), request.valor());
    }
}
