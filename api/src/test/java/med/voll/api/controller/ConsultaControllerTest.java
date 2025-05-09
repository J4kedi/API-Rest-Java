package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.DTO.DadosAgendamentoConsulta;
import med.voll.api.DTO.DadosCancelamentoConsulta;
import med.voll.api.DTO.DadosDetalhamentoConsulta;
import med.voll.api.model.Especialidade;
import med.voll.api.model.MotivoCancelamento;
import med.voll.api.service.AgendaDeConsultas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
    @Autowired JacksonTester<DadosCancelamentoConsulta> dadosCancelamentoJson;
    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver coódigo http 400 quando informações estão inválidas")
    @WithMockUser
    void testAgendar() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver coódigo http 200 quadno informações estão válidas")
    @WithMockUser
    void tes2tAgendar() throws Exception {
        var data = LocalDateTime.now().plusHours(2);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 1l, 1l, data);
        
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc
                .perform(
                        post("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(1l, 1l, data, especialidade)
                            ).getJson())
                )
                .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                    dadosDetalhamento
                ).getJson();
        
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
    @WithMockUser
    void testCancelar() throws Exception {
        var response = mvc.perform(delete("/consultas"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 204 quando informações estão válidas")
    @WithMockUser
    void test2Cancelar() throws Exception {
        var motivoCancelamento = MotivoCancelamento.PACIENTE_DESISTIU;
        var dadosCancelamento = new DadosCancelamentoConsulta(1l, motivoCancelamento);

        var response = mvc
                .perform(
                        delete("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosCancelamentoJson.write(
                                dadosCancelamento
                            ).getJson())
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());       
    }
}
