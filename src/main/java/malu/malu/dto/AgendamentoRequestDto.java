package malu.malu.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import malu.malu.enums.TipoComunicacao;

public record AgendamentoRequestDto(
    @NotBlank(message = "O destinatario é obrigatorio")
    String destinatario,

    @NotBlank(message = "A mensagem é obrigatoria")
    String mensagem,

    @NotBlank(message = "O tipo de comunicacao e obrigatorio")
    TipoComunicacao tipoComunicacao,

    @NotBlank(message = "A data é obrigatoria")
    @Future(message = "A data de envio deve ser uma data futura")
    LocalDateTime dataHoraEnvio
) {

}
