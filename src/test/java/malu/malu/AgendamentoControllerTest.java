package malu.malu;


import static org.mockito.Mockito.*;

import java.time.LocalDateTime;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import malu.malu.controller.AgendamentoController;
import malu.malu.dto.AgendamentoRequestDto;
import malu.malu.dto.AgendamentoResponseDto;
import malu.malu.enums.StatusAgendamento;
import malu.malu.enums.TipoComunicacao;
import malu.malu.service.AgendamentoService;

@WebMvcTest(AgendamentoController.class)
@RequiredArgsConstructor
public class AgendamentoControllerTest {

    //simula req http
    @Autowired
    private MockMvc mockMvc;

    //converte objetos json
    @Autowired
    private ObjectMapper ObjectMapper;

    //cria mock do service
    //evita chamada de reais ao banco de dados
    @MockitoBean
    private AgendamentoService service;

    @Test
    void deveAgendarComunicacao() throws Exception{
        AgendamentoRequestDto requestDto = new AgendamentoRequestDto(
            "cliente@gmail.com",
            "Mensagem teste",
            TipoComunicacao.EMAIL,
            LocalDateTime.now().plusMinutes(1)
        );

        AgendamentoResponseDto response = new AgendamentoResponseDto(
            1L,
            requestDto.destinatario(),
            requestDto.mensagem(),
            requestDto.tipoComunicacao(),
            requestDto.dataHoraEnvio(),
            StatusAgendamento.AGENDADO,
            LocalDateTime.now()
        );

        when(service.agendar(any())).thenReturn(response);

        mockMvc.perform(
            post("/api/agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.destinatario").value("cliente@gmail.com")
        );
    }

    @Test
    void deveBuscarAgendamentoPorId() throws Exception{
        Long id = 1L;

         AgendamentoRequestDto requestDto = new AgendamentoRequestDto(
            "cliente@gmail.com",
            "Mensagem teste",
            TipoComunicacao.SMS,
            LocalDateTime.now().plusMinutes(2)
        );

        AgendamentoResponseDto response = new AgendamentoResponseDto(
            1L,
            requestDto.destinatario(),
            requestDto.mensagem(),
            requestDto.tipoComunicacao(),
            requestDto.dataHoraEnvio(),
            StatusAgendamento.AGENDADO,
            LocalDateTime.now()
        );

        when(service.buscarPorId(id)).thenReturn(response);

        mockMvc.perform(get("/api/agendamentos/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.tipoComunicacao").value("SMS"));
    }

    @Test
    void  deveRemoverAgendamento() throws Exception{
        Lond id = 1L;
        
        doNothing().when(service.remover(id));

        mockMvc.perfom(delete("/api/agendamentos/{id}", id))
            .andExpect(status().isNoContent());
    }
}