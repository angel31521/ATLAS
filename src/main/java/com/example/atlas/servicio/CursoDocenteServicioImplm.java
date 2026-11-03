package com.example.atlas.servicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.CursoDocenteModel;
import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.CursoDocenteRepositorio;
import com.example.atlas.repositorio.CursoRepositorio;
import com.example.atlas.repositorio.UsuarioRepositorio;

@Service
public class CursoDocenteServicioImplm implements CursoDocenteServicio {

    private final CursoRepositorio cursoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CursoDocenteRepositorio cursoDocenteRepositorio;
    private final UsuarioServicio usuarioServicio;

    public CursoDocenteServicioImplm(CursoRepositorio cursoRepositorio,
                                     UsuarioRepositorio usuarioRepositorio,
                                     CursoDocenteRepositorio cursoDocenteRepositorio,
                                     UsuarioServicio usuarioServicio) {
        this.cursoRepositorio = cursoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.cursoDocenteRepositorio = cursoDocenteRepositorio;
        this.usuarioServicio = usuarioServicio;
    }

    private Long institucionDe(Long coordinadorId) {
        InstitucionModel inst = usuarioServicio.buscarPorId(coordinadorId).getInstitucion();
        return inst == null ? null : inst.getId();
    }

    private CursoModel cursoDelCoordinador(Long cursoId, Long coordinadorId) {
        CursoModel curso = cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new IllegalStateException("El curso no existe."));

        if (!curso.getInstitucion().getId().equals(institucionDe(coordinadorId))) {
            throw new IllegalStateException("Ese curso no pertenece a tu institución.");
        }
        return curso;
    }

    @Override
    @Transactional
    public void asignar(Long cursoId, Long docenteId, String cargo, Long coordinadorId) {
        String cargoLimpio = cargo == null ? "" : cargo.trim();
        if (cargoLimpio.isEmpty()) {
            throw new IllegalStateException("El cargo es obligatorio.");
        }

        CursoModel curso = cursoDelCoordinador(cursoId, coordinadorId);

        UsuarioModel docente = usuarioRepositorio.findById(docenteId)
                .orElseThrow(() -> new IllegalStateException("Docente no encontrado."));

        if (docente.getRol() != Rol.DOCENTE
                || docente.getInstitucion() == null
                || !docente.getInstitucion().getId().equals(curso.getInstitucion().getId())) {
            throw new IllegalStateException("Ese docente no pertenece a tu institución.");
        }

        if (cursoDocenteRepositorio.existsByCursoIdAndDocenteIdAndCargoIgnoreCase(
                cursoId, docenteId, cargoLimpio)) {
            throw new IllegalStateException("Ese docente ya tiene ese cargo en este curso.");
        }

        cursoDocenteRepositorio.save(new CursoDocenteModel(curso, docente, cargoLimpio));
    }

    @Override
    @Transactional
    public void quitar(Long cursoId, Long asignacionId, Long coordinadorId) {
        CursoModel curso = cursoDelCoordinador(cursoId, coordinadorId);

        CursoDocenteModel asignacion = cursoDocenteRepositorio.findById(asignacionId)
                .orElseThrow(() -> new IllegalStateException("La asignación no existe."));

        if (!asignacion.getCurso().getId().equals(curso.getId())) {
            throw new IllegalStateException("Esa asignación no pertenece a este curso.");
        }

        cursoDocenteRepositorio.delete(asignacion);
    }
}