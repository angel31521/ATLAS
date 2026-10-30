package com.example.atlas.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.CursoRepositorio;

@Service 
public class CursoServicioImplm implements CursoServicio {



    private final CursoRepositorio cursoRepositorio;
    private final UsuarioServicio usuarioServicio;

    public CursoServicioImplm(CursoRepositorio cursoRepositorio, UsuarioServicio usuarioServicio){

    this.cursoRepositorio = cursoRepositorio;
    this.usuarioServicio = usuarioServicio;

}



    //Guarda el curso relacionandolo con la institucion
    @Override
    public void guardarCurso(CursoModel curso, Long coordinadorId) {
        
        UsuarioModel coordinador = usuarioServicio.buscarPorId(coordinadorId);
        InstitucionModel institucion = coordinador.getInstitucion();

        if (institucion == null) {
            throw new IllegalStateException(
                "El coordinador todavía no tiene una institución registrada"
            );
        }


    curso.setInstitucion(institucion);
    cursoRepositorio.save(curso);

    }



    @Override
    public List<CursoModel> listarCursosPorCoordinador(Long coordinadorId) {
      
    UsuarioModel coordinador = usuarioServicio.buscarPorId(coordinadorId);

    InstitucionModel institucion = coordinador.getInstitucion();

    if (institucion == null) {
        return new ArrayList<>();
    }

    return cursoRepositorio.findByInstitucionId(institucion.getId());

    }






}
