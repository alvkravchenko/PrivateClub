package com.example.privateclub.repository;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.exception.ParticipantNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Optional<Participant> findByEmail(String email);

    Optional<Participant> findByPhone(String phone);

    default Participant findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new ParticipantNotFoundException("Участник с id " + id + " не найден"));
    }

}
