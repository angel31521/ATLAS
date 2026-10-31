package com.example.atlas.modelo;

import jakarta.persistence.*;

@Entity
public class CursoDocenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private CursoModel curso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "docente_id")
    private UsuarioModel docente;

    @Column(nullable = false, length = 100)
    private String cargo;

    public CursoDocenteModel() {}

    public CursoDocenteModel(CursoModel curso, UsuarioModel docente, String cargo) {
        this.curso = curso;
        this.docente = docente;
        this.cargo = cargo;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public CursoModel getCurso() {return curso;}
    public void setCurso(CursoModel curso) {this.curso = curso;}

    public UsuarioModel getDocente() {return docente;}
    public void setDocente(UsuarioModel docente) {this.docente = docente;}

    public String getCargo() {return cargo;}
    public void setCargo(String cargo) {this.cargo = cargo;}
}
