package com.example.atlas.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;



@Entity
public class InstitucionModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String nombre;


    @OneToMany(mappedBy = "institucion")
    private List<UsuarioModel> usuarios = new ArrayList<>();


    @OneToMany(mappedBy = "institucion")
    private List<CursoModel> cursos = new ArrayList<>();

    public InstitucionModel() {}

    public InstitucionModel(Long id, String nombre, List<UsuarioModel> usuarios, List<CursoModel> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.usuarios = usuarios;
        this.cursos = cursos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<UsuarioModel> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioModel> usuarios) {
        this.usuarios = usuarios;
    }

    public List<CursoModel> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoModel> cursos) {
        this.cursos = cursos;
    }


    





}
