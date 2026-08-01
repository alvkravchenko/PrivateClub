package com.example.privateclub.service;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.repository.QrCodeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final ParticipantService participantService; // зависимость от сервиса участника, так как qr код существует только с участником

    public QrCodeService(QrCodeRepository qrCodeRepository, ParticipantService participantService) {
        this.qrCodeRepository = qrCodeRepository;
        this.participantService = participantService;
    }

    public QrCode createQrForParticipant(UUID participantId) { // принимает id участника
        Participant participant = participantService.findParticipantById(participantId); // поиск участника
        QrCode code = new QrCode();
        code.setId(UUID.randomUUID());
        code.setParticipant(participant);
        code.setActive(true);
        return qrCodeRepository.save(code);
    }

    public Participant enterQrCode(UUID qrCodeParticipant){
        QrCode qrCode = qrCodeRepository.findByQrCode(qrCodeParticipant).orElseThrow(()-> new RuntimeException("QR-код не найден"));
        // продолжить тут
    }

}
