package com.example.privateclub.controller;

import com.example.privateclub.dto.ParticipantCreateDTO;
import com.example.privateclub.dto.ParticipantResponseDTO;
import com.example.privateclub.entity.Participant;
import com.example.privateclub.integration.BaseIntegrationTest;
import com.example.privateclub.repository.ParticipantRepository;
import com.example.privateclub.repository.QrCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ParticipantControllerTest extends BaseIntegrationTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Test
    void createParticipant_ShouldReturnCreatedParticipant() throws Exception {
        ParticipantCreateDTO createDTO = new ParticipantCreateDTO();
        createDTO.setFirstName("Vasya");
        createDTO.setLastName("Petrov");
        createDTO.setEmail("petrov@webclient.ru");
        createDTO.setPhone("+79000004567");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName").value("Vasya"))
                .andExpect(jsonPath("$.lastName").value("Petrov"))
                .andExpect(jsonPath("$.email").value("petrov@webclient.ru"))
                .andExpect(jsonPath("$.phone").value("+79000004567"))
                .andExpect(jsonPath("$.id").exists());

        List<Participant> saved = participantRepository.findAll();
        assertThat(saved.size()).isEqualTo(1);
    }

    @Test
    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/participants.sql"
    })
    void getAllParticipants_ShouldReturnAllParticipants() throws Exception {
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/participants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ParticipantResponseDTO[] response = objectMapper.readValue(responseJson, ParticipantResponseDTO[].class);

        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(4);
    }

    @Test
    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/participants.sql"
    })
    void getParticipantById_ShouldReturnParticipant() throws Exception {
        UUID participantId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/participants/{id}", participantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(participantId.toString()))
                .andExpect(jsonPath("$.firstName").value("Oleg"))
                .andExpect(jsonPath("$.lastName").value("Veshiy"))
                .andExpect(jsonPath("$.email").value("veshiy@webclient.ru"))
                .andExpect(jsonPath("$.phone").value("+79007778899"));
    }

    @Test
    @Sql(scripts = {
            "classpath:db/clean.sql",
            "classpath:sql/participants.sql"
    })
    void deleteParticipant_ShouldDeleteParticipant() throws Exception {
        UUID participantId = UUID.fromString("223e4567-e89b-12d3-a456-426614174001");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/participants/{id}", participantId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(participantRepository.findById(participantId)).isEmpty();
    }
}