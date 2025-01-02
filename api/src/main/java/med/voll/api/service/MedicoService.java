package med.voll.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.DTO.AtualizacaoMedicoDto;
import med.voll.api.DTO.ListagemMedicoDto;
import med.voll.api.DTO.MedicoDto;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;

    public void cadastrar(MedicoDto dados) {
        repository.save(new Medico(dados));
    }

    public Page<ListagemMedicoDto> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(ListagemMedicoDto::new);
    }

    public void atualizarMedico(AtualizacaoMedicoDto dados) {
        repository.getReferenceById(dados.id()).atualizarInfo(dados);
    }

    public void removeMedico(Long id) {
        repository.getReferenceById(id).excluir();
    }
}
