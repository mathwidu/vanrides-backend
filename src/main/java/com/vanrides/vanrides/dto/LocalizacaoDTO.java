package com.vanrides.vanrides.dto;

public class LocalizacaoDTO {

    private Double latitude;
    private Double longitude;
    private Long idMotorista;

    public LocalizacaoDTO() {}

    public LocalizacaoDTO(Double latitude, Double longitude, Long idMotorista) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idMotorista = idMotorista;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
    }
}
