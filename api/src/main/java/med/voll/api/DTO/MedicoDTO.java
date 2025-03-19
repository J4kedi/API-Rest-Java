package med.voll.api.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.Especialidade;

public record MedicoDto(
    @NotBlank String nome, 
    @NotBlank @Email String email, 
    @NotBlank String telefone, 
    @NotBlank String crm, 
    @NotNull Especialidade especialidade, 
    @NotNull @Valid EnderecoDto endereco
) {}