package com.example.privateclub.repository;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Optional<Participant> findByEmail(String email);

    Optional<Participant> findByPhone(String phone);

    default Participant findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("Участник с id " + id + " не найден"));
    }

}
