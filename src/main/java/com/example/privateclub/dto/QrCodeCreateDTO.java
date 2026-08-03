package com.example.privateclub.dto;

import com.example.privateclub.entity.QrCode;

import java.util.UUID;

public class QrCodeCreateDTO {
    private UUID participantId;

    public QrCodeCreateDTO() {

    }

    public QrCodeCreateDTO(UUID participantId) {
        this.participantId = participantId;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    public void setParticipantId(UUID participantId) {
        this.participantId = participantId;
    }
}
