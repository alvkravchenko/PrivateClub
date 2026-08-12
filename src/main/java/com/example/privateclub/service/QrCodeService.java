package com.example.privateclub.service;

import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.exception.ConflictException;
import com.example.privateclub.exception.NotFoundException;
import com.example.privateclub.mappers.ParticipantMapper;
import com.example.privateclub.mappers.QrCodeMapper;
import com.example.privateclub.repository.QrCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class QrCodeService {

    private final QrCodeRepository repository;
    private final ParticipantService participantService; // зависимость от сервиса участника, так как qr код существует только с участником
    private final QrCodeMapper qrCodeMapper;
    private final ParticipantMapper participantMapper;

    public QrCodeService(QrCodeRepository qrCodeRepository, ParticipantService participantService,
                         QrCodeMapper qrCodeMapper, ParticipantMapper participantMapper) {
        this.repository = qrCodeRepository;
        this.participantService = participantService;
        this.qrCodeMapper = qrCodeMapper;
        this.participantMapper = participantMapper;
    }

    public List<QrCodeResponseDTO> findQrCodesByParticipantId(UUID participantId) {
        return repository.findByParticipantId(participantId).stream()
                .map(qrCodeMapper::toResponseDTO)
                .toList();
    }

    public QrCodeResponseDTO findByQrCodeId(UUID id) {
        QrCode qrCode = repository.findByIdOrThrow(id);
        return qrCodeMapper.toResponseDTO(qrCode);
    }

    public QrCodeResponseDTO findByQrCodeValue(UUID qrCodeValue) {
        QrCode qrCode = repository.findByQrCodeValue(qrCodeValue)
                .orElseThrow(() -> new NotFoundException("QR-код не найден"));
        return qrCodeMapper.toResponseDTO(qrCode);
    }

    @Transactional
    public QrCodeResponseDTO createQrForParticipant(UUID participantId) { // принимает id участника
        Participant participant = participantService.findParticipantEntityById(participantId);
        QrCode code = new QrCode();
        code.setQrCodeValue(UUID.randomUUID());
        code.setParticipant(participant);
        QrCode saved = repository.save(code);
        return qrCodeMapper.toResponseDTO(saved);
    }

    @Transactional
    public QrCodeResponseDTO updateQrCode(UUID id, QrCode updated) {
        QrCode inBase = repository.findByIdOrThrow(id);
        inBase.setParticipant(updated.getParticipant());
        QrCode saved = repository.save(inBase);
        return qrCodeMapper.toResponseDTO(saved);
    }

    @Transactional
    public ParticipantResponseDTO enterQrCode(UUID qrCodeValue) {  //  параметр — значение QR-кода
        QrCode qrCode = repository.findByQrCodeValue(qrCodeValue)
                .orElseThrow(() -> new NotFoundException("QR-код не найден"));

        if (!qrCode.isActive()) {
            throw new ConflictException("QR-код уже использован");
        }

        Participant participant = qrCode.getParticipant();
        qrCode.setActive(false);
        repository.save(qrCode);
        createQrForParticipant(participant.getId());

        return participantMapper.toResponseDTO(participant);
    }

    @Transactional
    public void deleteQrCode(UUID id) {
        QrCode qrCode = repository.findByIdOrThrow(id);
        qrCode.setDeleted(true);
        repository.save(qrCode);
    }

}
