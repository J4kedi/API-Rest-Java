package med.voll.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.DTO.AtualizacaoMedicoDto;
import med.voll.api.DTO.ListagemMedicoDto;
import med.voll.api.DTO.MedicoDto;
import med.voll.api.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoService servico;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoDto dados, UriComponentsBuilder uriBuilder) {
        var medicoId = servico.cadastrar(dados).getId();
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medicoId).toUri();
        return ResponseEntity.created(uri).body(dados);
    }

    @GetMapping
    public ResponseEntity<Page<ListagemMedicoDto>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(servico.listar(paginacao));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid AtualizacaoMedicoDto dados) {   
        return ResponseEntity.ok(servico.atualizarMedico(dados));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        servico.removeMedico(id);
        return ResponseEntity.noContent().build();
    }
}
