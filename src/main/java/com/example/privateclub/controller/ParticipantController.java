package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.ParticipantUpdateDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.mappers.ParticipantMapper;
import com.example.privateclub.service.ParticipantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService service;
    private final ParticipantMapper participantMapper;

    public ParticipantController(ParticipantService service, ParticipantMapper participantMapper) {
        this.service = service;
        this.participantMapper = participantMapper;
    }

    @GetMapping
    public List<ParticipantResponseDTO> getAllParticipant() {
        List<Participant> participants = service.findAllParticipant();

        return participants.stream().map(p -> participantMapper.toResponseDTO(p)).toList();
    }

    @GetMapping("/{id}")
    public ParticipantResponseDTO getParticipantById(@PathVariable UUID id) {
        Participant participant = service.findParticipantById(id);
        return participantMapper.toResponseDTO(participant);
    }

    @PostMapping
    public ParticipantResponseDTO createParticipant(@RequestBody ParticipantCreateDTO createDTO) {
        Participant saved = service.addParticipant(createDTO);
        return participantMapper.toResponseDTO(saved);
    }

    @PutMapping("/{id}")
    public ParticipantResponseDTO updateParticipant(@PathVariable UUID id, @RequestBody ParticipantUpdateDTO updateDTO) {

        Participant saved = service.updateParticipant(id, updateDTO);
        return participantMapper.toResponseDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable UUID id) {
        service.deleteParticipant(id);
    }

}
