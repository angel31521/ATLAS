package com.example.atlas.servicio;

public interface CursoDocenteServicio {

    void asignar(Long cursoId, Long docenteId, String cargo, Long coordinadorId);

    void quitar(Long cursoId, Long asignacionId, Long coordinadorId);
}