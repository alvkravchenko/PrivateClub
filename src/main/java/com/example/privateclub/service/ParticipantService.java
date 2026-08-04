package com.example.privateclub.service;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.exception.BadRequestException;
import com.example.privateclub.exception.NotFoundException;
import com.example.privateclub.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Участник с id " + id + " не найден"));
    }

    public Participant addParticipant(Participant participant) {
        if (participant.getFirstName() == null || participant.getFirstName().isEmpty()) {
            throw new BadRequestException("Имя участника не может быть пустым");
        }
        if (participant.getLastName() == null || participant.getLastName().isEmpty()) {
            throw new BadRequestException("Фамилия участника не может быть пустой");
        }
        if (participant.getEmail() == null || participant.getEmail().isEmpty()) {
            throw new BadRequestException("Email участника не может быть пустым");
        }
        return repository.save(participant);
    }

    public Participant updateParticipant(UUID id, Participant updated) {
        Participant inBase = findParticipantById(id);
        inBase.setFirstName(updated.getFirstName());
        inBase.setLastName(updated.getLastName());
        inBase.setEmail(updated.getEmail());
        inBase.setPhone(updated.getPhone());
        return repository.save(inBase);
    }

    public void deleteParticipant(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Участник с ID " + id + " не найден");
        }
        repository.deleteById(id);
    }
}
