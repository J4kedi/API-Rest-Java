package med.voll.api.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.model.Especialidade;

public record MedicoDto(
    @NotBlank String nome, 
    @NotBlank @Email String email, 
    @NotBlank String telefone, 
    @NotBlank @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}") String crm, 
    @NotNull Especialidade especialidade, 
    @NotNull @Valid EnderecoDto endereco
) {}