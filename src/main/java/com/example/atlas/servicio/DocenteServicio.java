package com.example.atlas.servicio;

import java.util.List;

import com.example.atlas.dto.CursoAsignadoVista;
import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.modelo.UsuarioModel;

public interface DocenteServicio {

    // Panel del coordinador
    List<UsuarioModel> listarPorCoordinador(Long coordinadorId);

    void agregarPorCorreo(String correo, Long coordinadorId);

    void quitar(Long docenteId, Long coordinadorId);

    // Panel del docente
    List<CursoAsignadoVista> cursosDelDocente(Long docenteId);

    List<EstudianteModel> estudiantesDelDocente(Long docenteId);
}