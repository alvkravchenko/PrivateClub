package com.example.privateclub.service;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.repository.QrCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrCodeService {

    private final QrCodeRepository repository;
    private final ParticipantService participantService; // зависимость от сервиса участника, так как qr код существует только с участником

    public QrCodeService(QrCodeRepository qrCodeRepository, ParticipantService participantService) {
        this.repository = qrCodeRepository;
        this.participantService = participantService;
    }

    public QrCode findByQrCodeId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("QR код не найден"));
    }

    public QrCode createQrForParticipant(UUID participantId) { // принимает id участника
        Participant participant = participantService.findParticipantById(participantId); // поиск участника
        QrCode code = new QrCode();
        code.setParticipant(participant);
        return repository.save(code);
    }

    public QrCode updateQrCode(UUID id, QrCode updated) {
        QrCode inBase = findByQrCodeId(id);
        inBase.setParticipant(updated.getParticipant());
        return repository.save(inBase);
    }

    public Participant enterQrCode(UUID qrCodeParticipant) {
        QrCode qrCode = findByQrCodeId(qrCodeParticipant);
        Participant participant = qrCode.getParticipant();
        qrCode.setActive(false);
        repository.save(qrCode);
        createQrForParticipant(participant.getId());
        return participant;
    }

}
