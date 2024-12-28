package med.voll.api.DTO;

import med.voll.api.endereco.DadosEndereco;
import med.voll.api.model.Especialidade;

public record MedicoDTO(String nome, String email, String crm, Especialidade especialidade, DadosEndereco endereco) {}

