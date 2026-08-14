package com.example.privateclub.dto;

import com.example.privateclub.entity.QrCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class QrCodeCreateDTO {
    private UUID participantId;

    public QrCodeCreateDTO() {

    }
}
