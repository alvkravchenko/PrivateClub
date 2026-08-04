package com.example.privateclub.service;

import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.exception.ConflictException;
import com.example.privateclub.exception.NotFoundException;
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

    public List<QrCode> findAllQrCodes(){
        return repository.findAll();
    }
    public QrCode findByQrCodeId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("QR код не найден"));
    }

    public QrCode findByQrCodeValue(UUID qrCodeValue) {
        return repository.findByQrCodeValue(qrCodeValue)
                .orElseThrow(() -> new NotFoundException("QR-код не найден"));
    }

    public QrCode createQrForParticipant(UUID participantId) { // принимает id участника
        Participant participant = participantService.findParticipantById(participantId); // поиск участника
        QrCode code = new QrCode();
        code.setQrCodeValue(UUID.randomUUID());
        code.setParticipant(participant);
        return repository.save(code);
    }

    public QrCode updateQrCode(UUID id, QrCode updated) {
        QrCode inBase = findByQrCodeId(id);
        inBase.setParticipant(updated.getParticipant());
        return repository.save(inBase);
    }

    public Participant enterQrCode(UUID qrCodeValue) {  //  параметр — значение QR-кода
        QrCode qrCode = findByQrCodeValue(qrCodeValue);  //  поиск по значению
        if (!qrCode.isActive()) {
            throw new ConflictException("QR-код уже использован");
        }
        Participant participant = qrCode.getParticipant();
        qrCode.setActive(false);
        repository.save(qrCode);
        createQrForParticipant(participant.getId());
        return participant;
    }

    public void deleteQrCode(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Qr с ID " + id + " не найден");
        }

        repository.deleteById(id);
    }

}
