package com.example.privateclub.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;


    @Column(name = "qr_code", unique = true, nullable = false)
    private UUID qrCodeValue;

    @ManyToOne // связи, много объектов класса QrCode погут ссылаться к одному объекту класса Participant
    @JoinColumn(name = "participant_id", nullable = false)
    // @JoinColumn - внешний ключ (колонка participant_id в таблице qr_codes)
    private Participant participant; // Сам объект участника (связь с таблицей participants)

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public void setQrCodeValue(UUID qrCodeValue) {
        this.qrCodeValue = qrCodeValue;
    }

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;


    public QrCode (){

    }

    public QrCode(Participant participant, boolean isActive, LocalDateTime createdAt) {
        this.participant = participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UUID getId() {
        return id;
    }

    public UUID getQrCodeValue() {
        return qrCodeValue;
    }

    public Participant getParticipant() {
        return participant;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
