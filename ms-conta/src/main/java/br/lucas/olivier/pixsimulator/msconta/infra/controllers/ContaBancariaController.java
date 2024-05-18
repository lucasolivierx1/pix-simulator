package br.lucas.olivier.pixsimulator.msconta.infra.controllers;

import br.lucas.olivier.pixsimulator.msconta.application.usecases.*;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ChavePixRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaResponseDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.DepositoRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper.ChavePixDTOWrapper;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper.ContaBancariaDTOWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conta")
@Log4j2
public class ContaBancariaController {

    private final CadastrarContaBancariaUseCase cadastrarContaBancariaUseCase;
    private final AdicionarChavePixUseCase adicionarChavePixUseCase;
    private final BuscaContasBancariasUseCase buscaContasBancariasUseCase;
    private final BuscaChavesPixUseCase buscaChavesPixUseCase;
    private final DepositoUseCase depositoUseCase;

    public ContaBancariaController(final CadastrarContaBancariaUseCase cadastrarContaBancariaUseCase,
                                   final AdicionarChavePixUseCase adicionarChavePixUseCase,
                                   final BuscaContasBancariasUseCase buscaContasBancariasUseCase,
                                   final BuscaChavesPixUseCase buscaChavesPixUseCase,
                                   final DepositoUseCase depositoUseCase) {
        this.cadastrarContaBancariaUseCase = cadastrarContaBancariaUseCase;
        this.adicionarChavePixUseCase = adicionarChavePixUseCase;
        this.buscaContasBancariasUseCase = buscaContasBancariasUseCase;
        this.buscaChavesPixUseCase = buscaChavesPixUseCase;
        this.depositoUseCase = depositoUseCase;
    }

    @GetMapping
    public List<ContaBancariaResponseDTO> listaContaBancaria() {
        log.info("GET > /api/conta > Listando contas bancárias");
        return buscaContasBancariasUseCase.execute()
                .stream()
                .map(contaBancaria -> {

                    var chavesPix = buscaChavesPixUseCase.execute(contaBancaria.getId())
                            .stream().map(ChavePixDTOWrapper::toResponseDTO)
                            .toList();

                    return ContaBancariaDTOWrapper.toResponseDTO(contaBancaria, chavesPix);

                }).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaBancariaResponseDTO cadastrarContaBancaria(@RequestBody ContaBancariaRequestDTO request) {
        log.info("POST > /api/conta > Cadastrando conta bancária");
        return Optional.ofNullable(cadastrarContaBancariaUseCase
                        .execute(request.agencia(), request.numero(), request.digito(), request.tipoConta()))
                .map(contaBancaria ->
                        ContaBancariaDTOWrapper.toResponseDTO(contaBancaria, List.of()))
                .orElseThrow(() -> new PixSimulatorException("Erro ao cadastrar conta bancária"));
    }

    @PutMapping("/{idConta}/add-chave-pix")
    public ResponseEntity adicionarChavePix(@PathVariable String idConta, @RequestBody ChavePixRequestDTO request) {
        log.info("PUT > /api/conta > Adicionando chave pix");
        adicionarChavePixUseCase.execute(idConta, request.chave(), request.tipoChave());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idConta}/deposito")
    public ResponseEntity deposito(@PathVariable String idConta,
                                   @RequestBody DepositoRequestDTO request) {
        log.info("PUT > /api/conta > Realizando depósito");
        depositoUseCase.execute(idConta, request.valor());
        return ResponseEntity.noContent().build();
    }

}
