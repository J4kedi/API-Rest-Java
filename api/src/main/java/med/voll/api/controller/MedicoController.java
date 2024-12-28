package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.DTO.MedicoDTO;
import med.voll.api.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoService servico;

    @PostMapping
    public void cadastrar(@RequestBody MedicoDTO dados) {
        servico.cadastrar(dados);
    }
}
