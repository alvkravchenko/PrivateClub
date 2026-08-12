package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.ParticipantUpdateDTO;
import com.example.privateclub.service.ParticipantService;
import jakarta.validation.Valid;
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
        return service.findAllParticipant();
    }

    @GetMapping("/{id}")
    public ParticipantResponseDTO getParticipantById(@PathVariable UUID id) {
        return service.findParticipantById(id);
    }

    @PostMapping
    public ParticipantResponseDTO createParticipant(@Valid @RequestBody ParticipantCreateDTO createDTO) {
        return service.addParticipant(createDTO);
    }

    @PutMapping("/{id}")
    public ParticipantResponseDTO updateParticipant(@PathVariable UUID id, @Valid @RequestBody ParticipantUpdateDTO updateDTO) {
        return service.updateParticipant(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable UUID id) {
        service.deleteParticipant(id);
    }

}
