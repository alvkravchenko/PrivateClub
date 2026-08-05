package com.example.privateclub.mappers;

import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.QrCode;

public class QrCodeMapper {
    public QrCodeResponseDTO responseDTO(QrCode qrCode){
        return new QrCodeResponseDTO(
                qrCode.getId(),
                qrCode.getQrCodeValue(),
                qrCode.getParticipant().getId(),
                qrCode.isActive(),
                qrCode.getCreatedAt()
        );
    }
}
