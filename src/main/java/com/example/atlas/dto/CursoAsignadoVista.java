package com.example.atlas.dto;

public class CursoAsignadoVista {

    private final Long cursoId;
    private final String nombreCurso;
    private final String cargos;

    public CursoAsignadoVista(Long cursoId, String nombreCurso, String cargos) {
        this.cursoId = cursoId;
        this.nombreCurso = nombreCurso;
        this.cargos = cargos;
    }

    public Long getCursoId() {return cursoId;}
    public String getNombreCurso() {return nombreCurso;}
    public String getCargos() {return cargos;}
}
