package com.example.privateclub.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParticipantResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;

    public ParticipantResponseDTO() {

    }

    public ParticipantResponseDTO(UUID id, String firstName, String lastName, String email, String phone, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public String getPhone() {
        return phone;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
