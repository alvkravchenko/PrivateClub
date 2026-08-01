package com.example.privateclub.repository;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {


}
