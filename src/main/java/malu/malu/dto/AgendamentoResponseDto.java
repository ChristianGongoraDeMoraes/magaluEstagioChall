package malu.malu.dto;

import java.time.LocalDateTime;

import malu.malu.enums.StatusAgendamento;
import malu.malu.enums.TipoComunicacao;

public record AgendamentoResponseDto(
    Long id,
    String destinatario,
    String mensagem,
    TipoComunicacao tipoComunicacao,
    LocalDateTime dataHoraEnvio,
    StatusAgendamento status,
    LocalDateTime criadoEm
) {

}
