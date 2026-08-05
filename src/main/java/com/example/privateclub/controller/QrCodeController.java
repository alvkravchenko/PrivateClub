package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.QrCodeCreateDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.mappers.ParticipantMapper;
import com.example.privateclub.mappers.QrCodeMapper;
import com.example.privateclub.repository.QrCodeRepository;
import com.example.privateclub.service.QrCodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeService service;
    private final ParticipantMapper participantMapper;
    private final QrCodeMapper qrCodeMapper;

    public QrCodeController(QrCodeService service, ParticipantMapper participantMapper, QrCodeMapper qrCodeMapper) {
        this.service = service;
        this.participantMapper = participantMapper;
        this.qrCodeMapper= qrCodeMapper;
    }

    @GetMapping
    public List<QrCodeResponseDTO> getAllQrCodes() {
        List<QrCode> qrCodes = service.findAllQrCodes();
        return qrCodes.stream().map(p -> qrCodeMapper.responseDTO(p)).toList();
    }

    @GetMapping("/{id}")
    public QrCodeResponseDTO getQrCodeById(@PathVariable UUID id) {
        QrCode qrCode = service.findByQrCodeId(id);
        return qrCodeMapper.responseDTO(qrCode);
    }

    @PostMapping
    public QrCodeResponseDTO createQrCodeByParticipiant(@RequestBody QrCodeCreateDTO codeCreateDTO) {
        QrCode code = service.createQrForParticipant(codeCreateDTO.getParticipantId());
        return qrCodeMapper.responseDTO(code);
    }

    @PostMapping("/enter/{qrCode}")
    public ParticipantResponseDTO enterByQrCode(@PathVariable UUID qrCode) {
        Participant participant = service.enterQrCode(qrCode);

        return participantMapper.toResponseDTO(participant);
    }

    @DeleteMapping("/{id}")
    public void deleteQrCodes(@PathVariable UUID id){
      service.deleteQrCode(id);
    }
}
