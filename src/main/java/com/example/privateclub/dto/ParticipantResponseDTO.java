package com.example.privateclub.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ParticipantResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}
