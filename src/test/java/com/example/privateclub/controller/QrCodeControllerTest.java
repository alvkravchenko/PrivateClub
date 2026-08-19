package com.example.privateclub.controller;

import com.example.privateclub.dto.QrCodeCreateDTO;
import com.example.privateclub.dto.QrCodeResponseDTO;
import com.example.privateclub.integration.BaseIntegrationTest;
import com.example.privateclub.repository.ParticipantRepository;
import com.example.privateclub.repository.QrCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class QrCodeControllerTest extends BaseIntegrationTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Test

    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/qr_codes.sql"
    })
    void createQrCode_ShouldReturnCreatedQrCode() throws Exception {
        UUID participantId = UUID.fromString("333e4567-e89b-12d3-a456-426614174002");

        QrCodeCreateDTO createDTO = new QrCodeCreateDTO();
        createDTO.setParticipantId(participantId);

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.post("/api/qrcodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        QrCodeResponseDTO response = objectMapper.readValue(responseJson, QrCodeResponseDTO.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getParticipantId()).isEqualTo(participantId);
        assertThat(response.isActive()).isTrue();
    }

    @Test
    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/qr_codes.sql"
    })
    void getQrCodesByParticipant_ShouldReturnAllQrCodes() throws Exception {
        UUID participantId = UUID.fromString("333e4567-e89b-12d3-a456-426614174002");

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcodes/participant/{participantId}", participantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        QrCodeResponseDTO[] response = objectMapper.readValue(responseJson, QrCodeResponseDTO[].class);

        assertThat(response).isNotNull();

        long activeCount = Arrays.stream(response)
                .filter(QrCodeResponseDTO::isActive)
                .count();

        assertThat(activeCount).isEqualTo(1);

        for (QrCodeResponseDTO qr : response) {
            assertThat(qr.getParticipantId()).isEqualTo(participantId);
        }
    }

    @Test
    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/qr_codes.sql"
    })
    void getQrCodeById_ShouldReturnQrCode() throws Exception {
        UUID qrCodeId = UUID.fromString("444e4567-e89b-12d3-a456-426614174003");
        UUID qrCodeValue = UUID.fromString("555e4567-e89b-12d3-a456-426614174004");
        UUID participantId = UUID.fromString("333e4567-e89b-12d3-a456-426614174002");

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcodes/{id}", qrCodeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        QrCodeResponseDTO response = objectMapper.readValue(responseJson, QrCodeResponseDTO.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(qrCodeId);
        assertThat(response.getQrCodeValue()).isEqualTo(qrCodeValue);
        assertThat(response.getParticipantId()).isEqualTo(participantId);
        assertThat(response.isActive()).isTrue();
    }

    @Test
    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/qr_codes.sql"
    })
    void deleteQrCode_ShouldSoftDeleteQrCode() throws Exception {
        UUID qrCodeId = UUID.fromString("444e4567-e89b-12d3-a456-426614174003");

        assertThat(qrCodeRepository.findById(qrCodeId)).isPresent();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/qrcodes/{id}", qrCodeId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(qrCodeRepository.findById(qrCodeId)).isEmpty();
    }
}