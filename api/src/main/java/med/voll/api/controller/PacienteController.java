package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.DTO.AtualizacaoPacienteDto;
import med.voll.api.DTO.ListagemPacienteDto;
import med.voll.api.DTO.PacienteDto;
import med.voll.api.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteService servico;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid PacienteDto dados) {
        servico.cadastrar(dados);
    }

    @GetMapping
    public Page<ListagemPacienteDto> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return servico.listar(paginacao);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid AtualizacaoPacienteDto dados) {
        servico.atualizarPaciente(dados);   
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        servico.removePaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(servico.detalhaPaciente(id));
    }
}
