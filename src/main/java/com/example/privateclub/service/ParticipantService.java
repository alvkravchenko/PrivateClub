package com.example.privateclub.service;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.ParticipantUpdateDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.exception.ConflictException;
import com.example.privateclub.exception.ParticipantNotFoundException;
import com.example.privateclub.mappers.ParticipantMapper;
import com.example.privateclub.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class ParticipantService {

    private final ParticipantRepository repository;
    private final ParticipantMapper participantMapper;

    public ParticipantService(ParticipantRepository repository, ParticipantMapper participantMapper) {
        this.repository = repository;
        this.participantMapper = participantMapper;
    }

    public List<ParticipantResponseDTO> findAllParticipant() {
        return repository.findAll().stream()
                .map(participantMapper::toResponseDTO)
                .toList();
    }

    public ParticipantResponseDTO findParticipantById(UUID id) {
        Participant participant = repository.findByIdOrThrow(id);
        return participantMapper.toResponseDTO(participant);
    }

    @Transactional
    public ParticipantResponseDTO addParticipant(ParticipantCreateDTO createDTO) {
        if (repository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new ConflictException("Участник с таким Email существует");
        }
        if (createDTO.getPhone() != null && repository.findByPhone(createDTO.getPhone()).isPresent()) {
            throw new ConflictException("Участник с таким phone существует");
        }
        Participant participant = participantMapper.toEntity(createDTO);
        Participant saved = repository.save(participant);
        return participantMapper.toResponseDTO(saved);
    }

    @Transactional
    public ParticipantResponseDTO updateParticipant(UUID id, ParticipantUpdateDTO updateDTO) {
        Participant participantEntity = findParticipantEntityById(id);
        participantMapper.updateEntity(participantEntity, updateDTO);
        Participant saved = repository.save(participantEntity);
        return participantMapper.toResponseDTO(saved);
    }

    @Transactional
    public void deleteParticipant(UUID id) {
        if (!repository.existsById(id)) {
            throw new ParticipantNotFoundException("Участник с ID " + id + " не найден");
        }
        repository.deleteById(id);
    }

    public Participant findParticipantEntityById(UUID id) {
        return repository.findByIdOrThrow(id);
    }
}