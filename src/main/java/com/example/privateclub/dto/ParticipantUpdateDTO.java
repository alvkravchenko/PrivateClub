package com.example.privateclub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ParticipantUpdateDTO {
    @NotBlank(message = "Имя участника не может быть пустым")
    private String firstName;
    @NotBlank(message = "Фамилия участника не может быть пустой")
    private String lastName;
    @NotBlank(message = "Email участника не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;
    private String phone;

    public ParticipantUpdateDTO() {

    }

    public ParticipantUpdateDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
