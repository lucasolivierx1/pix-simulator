package br.lucas.olivier.pixsimulator.msconta.infra.controllers;

import br.lucas.olivier.pixsimulator.msconta.application.usecases.AdicionarChavePixUseCase;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.BuscaChavesPixUseCase;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.BuscaContasBancariasUseCase;
import br.lucas.olivier.pixsimulator.msconta.application.usecases.CadastrarContaBancariaUseCase;
import br.lucas.olivier.pixsimulator.msconta.domain.exceptions.PixSimulatorException;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ChavePixRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaRequestDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.dtos.ContaBancariaResponseDTO;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper.ChavePixDTOWrapper;
import br.lucas.olivier.pixsimulator.msconta.infra.controllers.wrapper.ContaBancariaDTOWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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

    public ContaBancariaController(final CadastrarContaBancariaUseCase cadastrarContaBancariaUseCase,
                                   final AdicionarChavePixUseCase adicionarChavePixUseCase,
                                   final BuscaContasBancariasUseCase buscaContasBancariasUseCase,
                                   final BuscaChavesPixUseCase buscaChavesPixUseCase) {
        this.cadastrarContaBancariaUseCase = cadastrarContaBancariaUseCase;
        this.adicionarChavePixUseCase = adicionarChavePixUseCase;
        this.buscaContasBancariasUseCase = buscaContasBancariasUseCase;
        this.buscaChavesPixUseCase = buscaChavesPixUseCase;
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
    public ContaBancariaResponseDTO cadastrarContaBancaria(@RequestBody ContaBancariaRequestDTO request) {
        log.info("POST > /api/conta > Cadastrando conta bancária");
        return Optional.ofNullable(cadastrarContaBancariaUseCase
                        .execute(request.agencia(), request.numero(), request.digito(), request.tipoConta()))
                .map(contaBancaria ->
                        ContaBancariaDTOWrapper.toResponseDTO(contaBancaria, List.of()))
                .orElseThrow(() -> new PixSimulatorException("Erro ao cadastrar conta bancária"));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarChavePix(@RequestParam String idConta, @RequestBody ChavePixRequestDTO request) {
        log.info("PUT > /api/conta > Adicionando chave pix");
        adicionarChavePixUseCase.execute(idConta, request.chave(), request.tipoChave());
    }
}
