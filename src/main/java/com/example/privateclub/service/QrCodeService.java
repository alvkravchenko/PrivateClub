package com.example.privateclub.service;

import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.exception.ConflictException;
import com.example.privateclub.exception.QrCodeNotFoundException;
import com.example.privateclub.mappers.ParticipantMapper;
import com.example.privateclub.mappers.QrCodeMapper;
import com.example.privateclub.repository.QrCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class QrCodeService {

    private final QrCodeRepository repository;
    private final ParticipantService participantService;
    private final QrCodeMapper qrCodeMapper;
    private final ParticipantMapper participantMapper;

    public QrCodeService(QrCodeRepository qrCodeRepository, ParticipantService participantService,
                         QrCodeMapper qrCodeMapper, ParticipantMapper participantMapper) {
        this.repository = qrCodeRepository;
        this.participantService = participantService;
        this.qrCodeMapper = qrCodeMapper;
        this.participantMapper = participantMapper;
    }

    @Transactional(readOnly = true)
    public List<QrCodeResponseDTO> findQrCodesByParticipantId(UUID participantId) {
        return repository.findByParticipantId(participantId).stream()
                .map(qrCodeMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public QrCodeResponseDTO findByQrCodeId(UUID id) {
        QrCode qrCode = repository.findByIdOrThrow(id);
        return qrCodeMapper.toResponseDTO(qrCode);
    }

    @Transactional
    public QrCodeResponseDTO createQrForParticipant(UUID participantId) {
        Participant participant = participantService.findParticipantEntityById(participantId);
        QrCode code = QrCode.builder()
                .qrCodeValue(UUID.randomUUID())
                .participant(participant)
                .isActive(true)
                .build();
        QrCode saved = repository.save(code);
        return qrCodeMapper.toResponseDTO(saved);
    }

    @Transactional
    public ParticipantResponseDTO enterQrCode(UUID qrCodeValue) {
        QrCode qrCode = repository.findByQrCodeValue(qrCodeValue)
                .orElseThrow(() -> new QrCodeNotFoundException("QR-код не найден"));

        if (!qrCode.isActive()) {
            throw new ConflictException("QR-код уже использован");
        }

        Participant participant = qrCode.getParticipant();
        qrCode.setActive(false);
        repository.save(qrCode);
        createQrForParticipant(participant.getId());

        return participantMapper.toResponseDTO(participant);
    }

    public void deleteQrCode(UUID id) {
        QrCode qrCode = repository.findByIdOrThrow(id);
        qrCode.setDeleted(true);
        repository.save(qrCode);
    }
}