package med.voll.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void cadastrar(@RequestBody @Valid MedicoDto dados) {
        servico.cadastrar(dados);
    }

    @GetMapping
    public Page<ListagemMedicoDto> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return servico.listar(paginacao);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid AtualizacaoMedicoDto dados) {
        var medico = servico.getMedicoId(dados.id());   
        medico.atualizarInfo(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        servico.removeMedico(id);
    }
}
