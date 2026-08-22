package com.example.privateclub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
@Getter
@Setter
@NoArgsConstructor
@SQLRestriction("deleted = false")
@Builder
@AllArgsConstructor
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "qr_code", unique = true, nullable = false)
    private UUID qrCodeValue;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

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