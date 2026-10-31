package com.example.atlas.servicio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atlas.dto.CursoAsignadoVista;
import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.CursoDocenteModel;
import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.CursoDocenteRepositorio;
import com.example.atlas.repositorio.EstudianteRepositorio;
import com.example.atlas.repositorio.UsuarioRepositorio;

@Service
public class DocenteServicioImplm implements DocenteServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioServicio usuarioServicio;
    private final CursoDocenteRepositorio cursoDocenteRepositorio;
    private final EstudianteRepositorio estudianteRepositorio;

    public DocenteServicioImplm(UsuarioRepositorio usuarioRepositorio,
                                UsuarioServicio usuarioServicio,
                                CursoDocenteRepositorio cursoDocenteRepositorio,
                                EstudianteRepositorio estudianteRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioServicio = usuarioServicio;
        this.cursoDocenteRepositorio = cursoDocenteRepositorio;
        this.estudianteRepositorio = estudianteRepositorio;
    }

    private InstitucionModel institucionDe(Long usuarioId) {
        return usuarioServicio.buscarPorId(usuarioId).getInstitucion();
    }

    // ---------- Para el panel del coordinador ----------

    @Override
    public List<UsuarioModel> listarPorCoordinador(Long coordinadorId) {
        InstitucionModel inst = institucionDe(coordinadorId);
        if (inst == null) {
            return List.of();
        }
        return usuarioRepositorio.findByInstitucionIdAndRol(inst.getId(), Rol.DOCENTE);
    }

    @Override
    @Transactional
    public void agregarPorCorreo(String correo, Long coordinadorId) {
        InstitucionModel inst = institucionDe(coordinadorId);
        if (inst == null) {
            throw new IllegalStateException("Primero debes registrar tu institución.");
        }

        String correoLimpio = correo == null ? "" : correo.trim();
        if (correoLimpio.isEmpty()) {
            throw new IllegalStateException("El correo es obligatorio.");
        }

        UsuarioModel docente = usuarioRepositorio.findByCorreoIgnoreCase(correoLimpio)
                .filter(u -> u.getRol() == Rol.DOCENTE)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró un docente registrado con ese correo."));

        if (docente.getInstitucion() != null) {
            if (docente.getInstitucion().getId().equals(inst.getId())) {
                throw new IllegalStateException("Ese docente ya está en tu institución.");
            }
            throw new IllegalStateException("Ese docente ya pertenece a otra institución.");
        }

        docente.setInstitucion(inst);
        usuarioRepositorio.save(docente);
    }

    @Override
    @Transactional
    public void quitar(Long docenteId, Long coordinadorId) {
        InstitucionModel inst = institucionDe(coordinadorId);

        UsuarioModel docente = usuarioRepositorio.findById(docenteId)
                .orElseThrow(() -> new IllegalStateException("Docente no encontrado."));

        if (inst == null
                || docente.getRol() != Rol.DOCENTE
                || docente.getInstitucion() == null
                || !docente.getInstitucion().getId().equals(inst.getId())) {
            throw new IllegalStateException("No tienes acceso a este docente.");
        }

        // Quitarlo también de todos sus cursos
        cursoDocenteRepositorio.deleteAll(cursoDocenteRepositorio.findByDocenteId(docenteId));

        docente.setInstitucion(null);
        usuarioRepositorio.save(docente);
    }

    // ---------- Para el panel del docente ----------

    // Un elemento por curso; si tiene varios cargos en él, van juntos
    @Override
    public List<CursoAsignadoVista> cursosDelDocente(Long docenteId) {
        InstitucionModel inst = institucionDe(docenteId);
        if (inst == null) {
            return List.of();
        }

        List<CursoDocenteModel> asignaciones =
                cursoDocenteRepositorio.findByDocenteIdAndCursoInstitucionId(docenteId, inst.getId());

        Map<Long, List<CursoDocenteModel>> porCurso = new LinkedHashMap<>();
        for (CursoDocenteModel a : asignaciones) {
            porCurso.computeIfAbsent(a.getCurso().getId(), k -> new ArrayList<>()).add(a);
        }

        List<CursoAsignadoVista> vistas = new ArrayList<>();
        for (List<CursoDocenteModel> grupo : porCurso.values()) {
            CursoModel curso = grupo.get(0).getCurso();
            String cargos = grupo.stream()
                    .map(CursoDocenteModel::getCargo)
                    .collect(Collectors.joining(", "));
            vistas.add(new CursoAsignadoVista(
                    curso.getId(),
                    curso.getGrado() + " - Grupo " + curso.getGrupo(),
                    cargos));
        }
        return vistas;
    }

    @Override
    public List<EstudianteModel> estudiantesDelDocente(Long docenteId) {
        List<Long> cursoIds = cursosDelDocente(docenteId).stream()
                .map(CursoAsignadoVista::getCursoId)
                .toList();

        if (cursoIds.isEmpty()) {
            return List.of();
        }
        return estudianteRepositorio.findByCursoIdIn(cursoIds);
    }
}
