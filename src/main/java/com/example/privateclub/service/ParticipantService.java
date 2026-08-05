package com.example.privateclub.service;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantUpdateDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.exception.BadRequestException;
import com.example.privateclub.exception.ConflictException;
import com.example.privateclub.exception.NotFoundException;
import com.example.privateclub.repository.ParticipantRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service

public class ParticipantService {

    private final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public List<Participant> findAllParticipant() {
        return repository.findAll();
    }

    public Participant findParticipantById(UUID id) {
        return repository.findByIdOrThrow(id);
    }

    @Transactional
    public Participant addParticipant(ParticipantCreateDTO createDTO) {

        if (repository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new ConflictException("Участник с таким Email существует");
        }
        if (repository.findByPhone(createDTO.getPhone()).isPresent()) {
            throw new ConflictException("Участник с таким phone существует");
        }
        Participant participant = new Participant(createDTO.getFirstName(),
                createDTO.getLastName(), createDTO.getEmail(), createDTO.getPhone());
        return repository.save(participant);
    }

    @Transactional
    public Participant updateParticipant(UUID id, ParticipantUpdateDTO updateDTO) {

        Participant inBase = findParticipantById(id);
        inBase.setFirstName(updateDTO.getFirstName());
        inBase.setLastName(updateDTO.getLastName());
        inBase.setEmail(updateDTO.getEmail());
        inBase.setPhone(updateDTO.getPhone());
        return repository.save(inBase);
    }

    @Transactional
    public void deleteParticipant(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Участник с ID " + id + " не найден");
        }
        repository.deleteById(id);
    }
}
