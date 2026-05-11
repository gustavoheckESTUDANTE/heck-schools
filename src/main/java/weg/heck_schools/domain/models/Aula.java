package weg.heck_schools.domain.models;

import java.time.LocalDateTime;

public class Aula {

    private long id;

    private long turmaId;

    private LocalDateTime dataHora;

    private String assunto;

    public Aula(long id, long turmaId, LocalDateTime dataHora, String assunto) {
        this.id = id;
        this.turmaId = turmaId;
        this.dataHora = dataHora;
        this.assunto = assunto;
    }

    public Aula(long turmaId, LocalDateTime dataHora, String assunto) {
        this.turmaId = turmaId;
        this.dataHora = dataHora;
        this.assunto = assunto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(long turmaId) {
        this.turmaId = turmaId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }
}
