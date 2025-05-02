package com.vanrides.vanrides.dto;

public class LoginResponse {

    private String tipoUsuario;
    private String mensagem;
    private Long id; // ✅ Novo campo

    // Construtor vazio
    public LoginResponse() {}

    // Construtor completo
    public LoginResponse(String tipoUsuario, String mensagem, Long id) {
        this.tipoUsuario = tipoUsuario;
        this.mensagem = mensagem;
        this.id = id;
    }

    // Getters e Setters
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
