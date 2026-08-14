package com.example.privateclub.controller;

import com.example.privateclub.dto.QrCodeCreateDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.entity.QrCode;
import com.example.privateclub.integration.BaseIntegrationTest;
import com.example.privateclub.repository.ParticipantRepository;
import com.example.privateclub.repository.QrCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class QrCodeControllerTest extends BaseIntegrationTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    private Participant testParticipant;

    @BeforeEach
    void cleanUp() {
        qrCodeRepository.deleteAll();
        participantRepository.deleteAll();

        testParticipant = new Participant("Тест", "Участник", "test@qr.ru", "+79001112233");
        testParticipant = participantRepository.save(testParticipant);
    }

    @Test
    void createQrCode_ShouldReturnCreatedQrCode() throws Exception {
        QrCodeCreateDTO createDTO = new QrCodeCreateDTO();
        createDTO.setParticipantId(testParticipant.getId());

        QrCodeResponseDTO response = webClient.post()
                .uri("/api/qrcodes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(createDTO))
                .retrieve()
                .bodyToMono(QrCodeResponseDTO.class)
                .block();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getParticipantId()).isEqualTo(testParticipant.getId());
        assertThat(response.isActive()).isTrue();

        QrCode saved = qrCodeRepository.findById(response.getId()).orElseThrow();
        assertThat(saved.getParticipant().getId()).isEqualTo(testParticipant.getId());
        assertThat(saved.isActive()).isTrue();
    }

    @Test
    void getQrCodesByParticipant_ShouldReturnAllQrCodes() {
        QrCode qr1 = new QrCode(testParticipant);
        qr1.setQrCodeValue(UUID.randomUUID());
        QrCode qr2 = new QrCode(testParticipant);
        qr2.setQrCodeValue(UUID.randomUUID());
        qrCodeRepository.save(qr1);
        qrCodeRepository.save(qr2);

        QrCodeResponseDTO[] response = webClient.get()
                .uri("/api/qrcodes/participant/{participantId}", testParticipant.getId())
                .retrieve()
                .bodyToMono(QrCodeResponseDTO[].class)
                .block();

        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(2);
        assertThat(response[0].getParticipantId()).isEqualTo(testParticipant.getId());
        assertThat(response[1].getParticipantId()).isEqualTo(testParticipant.getId());
    }

    @Test
    void getQrCodeById_ShouldReturnQrCode() {
        QrCode qrCode = new QrCode(testParticipant);
        qrCode.setQrCodeValue(UUID.randomUUID());
        QrCode saved = qrCodeRepository.save(qrCode);

        QrCodeResponseDTO response = webClient.get()
                .uri("/api/qrcodes/{id}", saved.getId())
                .retrieve()
                .bodyToMono(QrCodeResponseDTO.class)
                .block();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(saved.getId());
        assertThat(response.getQrCode()).isEqualTo(saved.getQrCodeValue());
        assertThat(response.getParticipantId()).isEqualTo(testParticipant.getId());
        assertThat(response.isActive()).isTrue();
    }

    @Test
    void deleteQrCode_ShouldSoftDeleteQrCode() {
        QrCode qrCode = new QrCode(testParticipant);
        qrCode.setQrCodeValue(UUID.randomUUID());
        QrCode saved = qrCodeRepository.save(qrCode);

        assertThat(qrCodeRepository.findById(saved.getId())).isPresent();

        webClient.delete()
                .uri("/api/qrcodes/{id}", saved.getId())
                .retrieve()
                .toBodilessEntity()
                .block();

        assertThat(qrCodeRepository.findById(saved.getId())).isEmpty();
    }
}