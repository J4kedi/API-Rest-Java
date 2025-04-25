package med.voll.api.service.validacoesConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.DTO.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferenciaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferenciaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com atencedência mínima de 30 minutos");
        }
    }
}
