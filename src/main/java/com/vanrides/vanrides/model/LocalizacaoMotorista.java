package com.vanrides.vanrides.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "localizacao_motorista")
public class LocalizacaoMotorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public LocalizacaoMotorista() {}

    public LocalizacaoMotorista(Motorista motorista, Double latitude, Double longitude, LocalDateTime dataHora) {
        this.motorista = motorista;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataHora = dataHora;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public Motorista getMotorista() { return motorista; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public LocalDateTime getDataHora() { return dataHora; }

    public void setId(Long id) { this.id = id; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
