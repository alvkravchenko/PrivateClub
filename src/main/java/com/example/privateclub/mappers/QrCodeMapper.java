package com.example.privateclub.mappers;

import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.QrCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QrCodeMapper {

    @Mapping(source = "participant.id", target = "participantId")
    QrCodeResponseDTO toResponseDTO(QrCode qrCode);
}