package weg.heck_schools.domain.models;

public class Turma {

    private long id;

    private String nome;

    private long cursoId;

    private long professorId;

    public Turma(long id, String nome, long cursoId, long professorId) {
        this.id = id;
        this.nome = nome;
        this.cursoId = cursoId;
        this.professorId = professorId;
    }

    public Turma(String nome, long cursoId, long professorId) {
        this.nome = nome;
        this.cursoId = cursoId;
        this.professorId = professorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCursoId() {
        return cursoId;
    }

    public void setCursoId(long cursoId) {
        this.cursoId = cursoId;
    }

    public long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
    }
}
