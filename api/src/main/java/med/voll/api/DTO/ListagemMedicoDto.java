package med.voll.api.DTO;

import med.voll.api.model.Endereco;
import med.voll.api.model.Especialidade;
import med.voll.api.model.Medico;

public record ListagemMedicoDto(
    Long id,
    String nome,
    String email,
    String telefone,
    String crm,
    Especialidade especialidade,
    Endereco endereco
) { 
    public ListagemMedicoDto(Medico m) {
        this(m.getId(), m.getNome(), m.getEmail(), m.getTelefone(), m.getCrm(), m.getEspecialidade(), m.getEndereco());
    }
}
