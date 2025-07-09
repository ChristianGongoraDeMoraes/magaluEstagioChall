package malu.malu.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import malu.malu.dto.AgendamentoRequestDto;
import malu.malu.dto.AgendamentoResponseDto;
import malu.malu.enums.StatusAgendamento;
import malu.malu.model.Agendamento;
import malu.malu.repository.AgendamentoRepository;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private AgendamentoRepository agendamentoRepository;

    public AgendamentoResponseDto agendar(AgendamentoRequestDto agendamentoRequestDto){
        Agendamento agendamento = Agendamento.builder()
            .destinatario(agendamentoRequestDto.destinatario())
            .mensagem(agendamentoRequestDto.mensagem())
            .tipoComunicacao(agendamentoRequestDto.tipoComunicacao())
            .dataHoraEnvio(agendamentoRequestDto.dataHoraEnvio())
            .status(StatusAgendamento.AGENDADO)
            .criadoEm(LocalDateTime.now())
            .build();

            return toResponse(agendamentoRepository.save(agendamento));
    }

    public AgendamentoResponseDto buscarPorId(Long id){
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        return toResponse(agendamento);
    }

    public void remover(Long id){
        if(!agendamentoRepository.existsById(id)){
            throw new EntityNotFoundException("Agendamento não encontrado");
        }
        agendamentoRepository.deleteById(id);
    }

    public AgendamentoResponseDto toResponse(Agendamento agendamento){
        return new AgendamentoResponseDto(
            agendamento.getId(),
            agendamento.getDestinatario(),
            agendamento.getMensagem(),
            agendamento.getTipoComunicacao(),
            agendamento.getDataHoraEnvio(),
            agendamento.getStatus(),
            agendamento.getCriadoEm()
        );
    }
}   
