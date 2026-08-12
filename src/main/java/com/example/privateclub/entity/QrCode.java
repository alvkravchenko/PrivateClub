package com.example.privateclub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
@Getter
@Setter
@NoArgsConstructor // пустой конструктор
@SQLRestriction("deleted = false")
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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;


    public QrCode(Participant participant) {
        this.participant = participant;
    }

}
