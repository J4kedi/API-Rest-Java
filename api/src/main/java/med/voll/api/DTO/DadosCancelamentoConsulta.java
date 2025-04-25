package med.voll.api.DTO;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.MotivoCancelamento;

public record DadosCancelamentoConsulta(
    @NotNull
    Long idConsulta,
    @NotNull
    MotivoCancelamento motivo
) {} 