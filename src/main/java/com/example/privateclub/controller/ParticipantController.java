package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.ParticipantUpdateDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.service.ParticipantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService service;

    public ParticipantController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping
    public List<ParticipantResponseDTO> getAllParticipant() {
        List<Participant> participants = service.findAllParticipant();

        return participants.stream().map(p -> new ParticipantResponseDTO(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getEmail(),
                p.getPhone(),
                p.getCreatedAt()
        )).toList();
    }

    @GetMapping("/{id}")
    public ParticipantResponseDTO getParticipantById(@PathVariable UUID id) {
        Participant participant = service.findParticipantById(id);
        return new ParticipantResponseDTO(participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail(),
                participant.getPhone(),
                participant.getCreatedAt());
    }

    @PostMapping
    public ParticipantResponseDTO createParticipant(@RequestBody ParticipantCreateDTO createDTO) {
        Participant participant = new Participant(createDTO.getFirstName(),
                createDTO.getLastName(), createDTO.getEmail(), createDTO.getPhone());
        Participant saved = service.addParticipant(participant);
        return new ParticipantResponseDTO(saved.getId(), saved.getFirstName(),
                saved.getLastName(), saved.getEmail(), saved.getPhone(), saved.getCreatedAt());
    }

    @PutMapping("/{id}")
    public ParticipantResponseDTO updateParticipant(@PathVariable UUID id, @RequestBody ParticipantUpdateDTO updateDTO) {
        Participant updatedParticipant = new Participant(
                updateDTO.getFirstName(),
                updateDTO.getLastName(),
                updateDTO.getEmail(),
                updateDTO.getPhone()
        );
        Participant saved = service.updateParticipant(id, updatedParticipant);
        return new ParticipantResponseDTO(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getCreatedAt()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable UUID id) {
        service.deleteParticipant(id);
    }

}
