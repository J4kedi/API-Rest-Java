package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import med.voll.api.DTO.EnderecoDto;
import med.voll.api.DTO.ListagemMedicoDto;
import med.voll.api.DTO.MedicoDto;
import med.voll.api.model.Endereco;
import med.voll.api.model.Especialidade;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<MedicoDto> dadosCadastroMedicoJson;
    @Autowired
    private JacksonTester<ListagemMedicoDto> dadosDetalhamentoMedicoJson;
    @MockitoBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria retornar com o código de erro 400 com as informações inválidas")
    @WithMockUser
    void testCadastrar() throws Exception{
        var response = mvc
                .perform(post("/medicos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar com o código 201 com as informaçõe válidas")
    @WithMockUser
    void test2Cadastrar() throws Exception{
        var dadosCadastro = new MedicoDto(
            "Medico",
            "medico@voll.med",
            "123456",
            "61999999999",
            Especialidade.CARDIOLOGIA,
            dadosEndereco());
        
        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mvc.perform(post("/medicos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                    .andReturn().getResponse();

        var dadosDetalhamento = new ListagemMedicoDto(
            null,
            dadosCadastro.nome(),
            dadosCadastro.email(),
            dadosCadastro.telefone(),
            dadosCadastro.crm(),
            dadosCadastro.especialidade(),
            new Endereco(dadosCadastro.endereco())
        );

        var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private EnderecoDto dadosEndereco() {
        return new EnderecoDto(
            "rua xpto",
            "bairro",
            "00000000",
            "Brasilia",
            "DF",
            null,
            null
        );
    }
}