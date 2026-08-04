package com.example.privateclub.mappers;

import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.entity.Participant;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {
    public ParticipantResponseDTO toResponseDTO(Participant participant) {
        return new ParticipantResponseDTO(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail(),
                participant.getPhone(),
                participant.getCreatedAt()
        );
    }

}
