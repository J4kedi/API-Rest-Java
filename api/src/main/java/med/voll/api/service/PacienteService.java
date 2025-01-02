package med.voll.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.DTO.AtualizacaoPacienteDto;
import med.voll.api.DTO.ListagemPacienteDto;
import med.voll.api.DTO.PacienteDto;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public void cadastrar(PacienteDto dados) {
        repository.save(new Paciente(dados));
    }

    public Page<ListagemPacienteDto> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(ListagemPacienteDto::new);
    }

    public void atualizarPaciente(AtualizacaoPacienteDto dados) {
        repository.getReferenceById(dados.id()).atualizarInfo(dados);
    }

    public void removePaciente(Long id) {
        repository.getReferenceById(id).excluir();
    }
}
