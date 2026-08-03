package com.example.privateclub.dto;

import com.example.privateclub.entity.QrCode;

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

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getQrCode() {
        return qrCode;
    }

    public void setQrCode(UUID qrCode) {
        this.qrCode = qrCode;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    public void setParticipantId(UUID participantId) {
        this.participantId = participantId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
