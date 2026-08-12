package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.dto.QrCodeCreateDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;

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

    @GetMapping("/participant/{participantId}")
    public List<QrCodeResponseDTO> getQrCodesByParticipant(@PathVariable UUID participantId) {
        return service.findQrCodesByParticipantId(participantId);
    }

    @GetMapping("/{id}")
    public QrCodeResponseDTO getQrCodeById(@PathVariable UUID id) {
        return service.findByQrCodeId(id);
    }

    @PostMapping
    public QrCodeResponseDTO createQrCodeByParticipiant(@RequestBody QrCodeCreateDTO codeCreateDTO) {
        return service.createQrForParticipant(codeCreateDTO.getParticipantId());
    }

    @PostMapping("/enter/{qrCode}")
    public ParticipantResponseDTO enterByQrCode(@PathVariable UUID qrCode) {
        return service.enterQrCode(qrCode);
    }

    @DeleteMapping("/{id}")
    public void deleteQrCodes(@PathVariable UUID id) {
        service.deleteQrCode(id);
    }
}
