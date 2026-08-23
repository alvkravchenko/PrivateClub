package com.example.privateclub.mappers;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.ParticipantUpdateDTO;
import com.example.privateclub.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    ParticipantResponseDTO toResponseDTO(Participant participant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Participant toEntity(ParticipantCreateDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Participant participant, ParticipantUpdateDTO updateDTO);
}