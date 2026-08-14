package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.integration.BaseIntegrationTest;
import com.example.privateclub.repository.ParticipantRepository;
import com.example.privateclub.repository.QrCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantControllerTest extends BaseIntegrationTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @BeforeEach
    void cleanUp() {
        qrCodeRepository.deleteAll();
        participantRepository.deleteAll();
    }

    @Test
    void createParticipant_ShouldReturnCreatedParticipant() throws Exception {
        ParticipantCreateDTO createDTO = new ParticipantCreateDTO();
        createDTO.setFirstName("Vasya");
        createDTO.setLastName("Petrov");
        createDTO.setEmail("petrov@webclient.ru");
        createDTO.setPhone("+79001234567");

        ParticipantResponseDTO response = webClient.post()
                .uri("/api/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(createDTO))
                .retrieve()
                .bodyToMono(ParticipantResponseDTO.class)
                .block();

        assertThat(response).isNotNull();
        assertThat(response.getFirstName()).isEqualTo("Vasya");
        assertThat(response.getLastName()).isEqualTo("Petrov");
        assertThat(response.getEmail()).isEqualTo("petrov@webclient.ru");
        assertThat(response.getPhone()).isEqualTo("+79001234567");
        assertThat(response.getId()).isNotNull();

        Participant saved = participantRepository.findById(response.getId()).orElseThrow();
        assertThat(saved.getFirstName()).isEqualTo("Vasya");
        assertThat(saved.getEmail()).isEqualTo("petrov@webclient.ru");
    }

    @Test
    void getAllParticipants_ShouldReturnAllParticipants() {
        Participant p1 = new Participant("Alex", "Sidorov", "alex@webclient.ru", "+79001112233");
        Participant p2 = new Participant("Mary", "Ivanova", "maria@webclient.ru", "+79004445566");
        participantRepository.save(p1);
        participantRepository.save(p2);

        ParticipantResponseDTO[] response = webClient.get()
                .uri("/api/participants")
                .retrieve()
                .bodyToMono(ParticipantResponseDTO[].class)
                .block();

        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(2);
    }

    @Test
    void getParticipantById_ShouldReturnParticipant() {
        Participant participant = new Participant("Oleg", "Veshiy", "veshiy@webclient.ru", "+79007778899");
        Participant saved = participantRepository.save(participant);

        ParticipantResponseDTO response = webClient.get()
                .uri("/api/participants/{id}", saved.getId())
                .retrieve()
                .bodyToMono(ParticipantResponseDTO.class)
                .block();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(saved.getId());
        assertThat(response.getFirstName()).isEqualTo("Oleg");
        assertThat(response.getEmail()).isEqualTo("veshiy@webclient.ru");
    }

    @Test
    void deleteParticipant_ShouldDeleteParticipant() {
        Participant participant = new Participant("Для", "Удаления", "delete@webclient.ru", "+79001112233");
        Participant saved = participantRepository.save(participant);

        webClient.delete()
                .uri("/api/participants/{id}", saved.getId())
                .retrieve()
                .toBodilessEntity()
                .block();

        assertThat(participantRepository.findById(saved.getId())).isEmpty();
    }
}