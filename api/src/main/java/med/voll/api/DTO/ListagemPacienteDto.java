package med.voll.api.DTO;

import med.voll.api.model.Endereco;
import med.voll.api.model.Paciente;

public record ListagemPacienteDto(Long id, String nome, String email, String cpf, Endereco endereco) {
    public ListagemPacienteDto(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getEndereco());
    }
}
