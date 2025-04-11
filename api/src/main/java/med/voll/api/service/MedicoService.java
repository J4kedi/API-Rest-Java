package med.voll.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.DTO.AtualizacaoMedicoDto;
import med.voll.api.DTO.EnderecoDto;
import med.voll.api.DTO.ListagemMedicoDto;
import med.voll.api.DTO.MedicoDto;
import med.voll.api.model.Endereco;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;

    public Medico cadastrar(MedicoDto dados) {
        var medico = new Medico(dados);
        repository.save(medico);
        return medico;
    }

    public Page<ListagemMedicoDto> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(ListagemMedicoDto::new);
    }

    public ListagemMedicoDto atualizarMedico(AtualizacaoMedicoDto dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInfo(dados);

        return new ListagemMedicoDto(medico);
    }

    public void removeMedico(Long id) {
        repository.getReferenceById(id).excluir();
    }

    private MedicoDto converteDadosMedico(Medico medico) {
        return new MedicoDto(
            medico.getNome(),
            medico.getEmail(),
            medico.getTelefone(),
            medico.getCrm(),
            medico.getEspecialidade(),
            converteDadosEndereco(medico.getEndereco())
        );
    }

    private EnderecoDto converteDadosEndereco(Endereco endereco) {
        return new EnderecoDto(
            endereco.getLogradouro(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getCidade(),
            endereco.getUf(),
            endereco.getNumero(),
            endereco.getComplemento()
        );
    }
    
}
