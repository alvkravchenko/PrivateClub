package com.example.privateclub.repository;


import com.example.privateclub.entity.QrCode;
import com.example.privateclub.exception.QrCodeNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByQrCodeValue(UUID qrCodeValue);

    default QrCode findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new QrCodeNotFoundException("QR-код с id " + id + " не найден"));
    }

    List<QrCode> findByParticipantId(UUID participantId);

}
