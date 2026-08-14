package com.example.privateclub.dto;

import com.example.privateclub.entity.QrCode;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class QrCodeResponseDTO {
    private UUID id;
    private UUID qrCode;
    private UUID participantId;
    private boolean isActive;
    private LocalDateTime createdAt;

    public QrCodeResponseDTO() {

    }

    public QrCodeResponseDTO(UUID id, UUID qrCode, UUID participantId, boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.qrCode = qrCode;
        this.participantId = participantId;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }


    public UUID getQrCode() {
        return qrCode;
    }


    public UUID getParticipantId() {
        return participantId;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
