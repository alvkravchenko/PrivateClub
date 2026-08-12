package com.example.privateclub.mappers;

import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.QrCode;
import org.springframework.stereotype.Component;

@Component
public class QrCodeMapper {
    public QrCodeResponseDTO toResponseDTO(QrCode qrCode){
        return new QrCodeResponseDTO(
                qrCode.getId(),
                qrCode.getQrCodeValue(),
                qrCode.getParticipant().getId(),
                qrCode.isActive(),
                qrCode.getCreatedAt()
        );
    }
}
