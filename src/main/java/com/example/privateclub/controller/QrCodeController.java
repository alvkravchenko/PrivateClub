package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.QrCodeCreateDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.repository.QrCodeRepository;
import com.example.privateclub.service.QrCodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeService service;

    public QrCodeController(QrCodeService service) {
        this.service = service;
    }

    @GetMapping
    public List<QrCodeResponseDTO> getAllQrCodes() {
        List<QrCode> qrCodes = service.findAllQrCodes();
        return qrCodes.stream().map(p -> new QrCodeResponseDTO(p.getId(),
                p.getQrCodeValue(), p.getParticipant().getId(), p.isActive(), p.getCreatedAt()
        )).toList();
    }

    @GetMapping("/{id}")
    public QrCodeResponseDTO getQrCodeById(@PathVariable UUID id) {
        QrCode qrCode = service.findByQrCodeId(id);
        return new QrCodeResponseDTO(qrCode.getId(), qrCode.getQrCodeValue(),
                qrCode.getParticipant().getId(), qrCode.isActive(), qrCode.getCreatedAt());
    }

    @PostMapping
    public QrCodeResponseDTO createQrCodeByParticipiant(@RequestBody QrCodeCreateDTO codeCreateDTO) {
        QrCode code = service.createQrForParticipant(codeCreateDTO.getParticipantId());
        return new QrCodeResponseDTO(code.getId(), code.getQrCodeValue(),
                code.getParticipant().getId(), code.isActive(), code.getCreatedAt());
    }

    @PostMapping("/enter/{qrCode}")
    public ParticipantResponseDTO enterByQrCode(@PathVariable UUID qrCode) {
        Participant participant = service.enterQrCode(qrCode);

        return new ParticipantResponseDTO(participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail(),
                participant.getPhone(),
                participant.getCreatedAt());
    }

    @DeleteMapping("/{id}")
    public void deleteQrCodes(@PathVariable UUID id){
      service.deleteQrCode(id);
    }
}
