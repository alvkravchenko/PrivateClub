package com.example.privateclub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParticipantCreateDTO {
    @NotBlank(message = "Имя участника не может быть пустым")
    private String firstName;
    @NotBlank(message = "Фамилия участника не может быть пустой")
    private String lastName;
    @NotBlank(message = "Email участника не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;
    private String phone;

}
