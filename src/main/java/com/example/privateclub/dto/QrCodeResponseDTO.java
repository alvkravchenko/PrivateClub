package com.example.privateclub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class QrCodeResponseDTO {
    private UUID id;
    private UUID qrCodeValue;
    private UUID participantId;
    private boolean isActive;
    private LocalDateTime createdAt;


    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

}
