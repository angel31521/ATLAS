package com.example.atlas.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.repositorio.CursoRepositorio;
import com.example.atlas.repositorio.EstudianteRepositorio;

@Service
public class EstudianteServicioImplm implements EstudianteServicio {

    private final EstudianteRepositorio estudianteRepositorio;
    private final CursoRepositorio cursoRepositorio;
    private final UsuarioServicio usuarioServicio;

    public EstudianteServicioImplm(EstudianteRepositorio estudianteRepositorio,
                                   CursoRepositorio cursoRepositorio,
                                   UsuarioServicio usuarioServicio) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.cursoRepositorio = cursoRepositorio;
        this.usuarioServicio = usuarioServicio;
    }

    // Id de la institución del coordinador (null si todavía no tiene una)
    private Long institucionDe(Long coordinadorId) {
        InstitucionModel inst = usuarioServicio.buscarPorId(coordinadorId).getInstitucion();
        return inst == null ? null : inst.getId();
    }

    @Override
    public List<EstudianteModel> listarPorCoordinador(Long coordinadorId) {
        Long institucionId = institucionDe(coordinadorId);
        if (institucionId == null) {
            return List.of();
        }
        return estudianteRepositorio.findByCursoInstitucionId(institucionId);
    }

    @Override
    public void guardar(EstudianteModel estudiante, Long cursoId, Long coordinadorId) {
        CursoModel curso = cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new IllegalStateException("El curso no existe."));

        if (!curso.getInstitucion().getId().equals(institucionDe(coordinadorId))) {
            throw new IllegalStateException("Ese curso no pertenece a tu institución.");
        }

        estudiante.setId(null);   // siempre se crea uno nuevo
        estudiante.setCurso(curso);
        estudianteRepositorio.save(estudiante);
    }

    // Devuelve el estudiante solo si pertenece a la institución del coordinador
    @Override
    public EstudianteModel buscarDelCoordinador(Long id, Long coordinadorId) {
        EstudianteModel e = estudianteRepositorio.findById(id)
                .orElseThrow(() -> new IllegalStateException("Estudiante no encontrado."));

        if (!e.getCurso().getInstitucion().getId().equals(institucionDe(coordinadorId))) {
            throw new IllegalStateException("No tienes acceso a este estudiante.");
        }
        return e;
    }

    @Override
    public void actualizar(Long id, EstudianteModel datos, Long coordinadorId) {
        EstudianteModel e = buscarDelCoordinador(id, coordinadorId);

        e.setNombre(datos.getNombre());
        e.setDireccion(datos.getDireccion());
        e.setEps_afiliada(datos.getEps_afiliada());
        e.setNombre_madre(datos.getNombre_madre());
        e.setNombre_padre(datos.getNombre_padre());
        e.setTelefono_madre(datos.getTelefono_madre());
        e.setTelefono_padre(datos.getTelefono_padre());

        estudianteRepositorio.save(e);
    }
}