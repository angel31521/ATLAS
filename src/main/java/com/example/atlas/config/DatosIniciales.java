package com.example.atlas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.servicio.CursoDocenteServicio;
import com.example.atlas.servicio.CursoServicio;
import com.example.atlas.servicio.DocenteServicio;
import com.example.atlas.servicio.EstudianteServicio;
import com.example.atlas.servicio.InstitucionServicio;
import com.example.atlas.servicio.UsuarioServicio;

@Configuration 
public class DatosIniciales {

     @Bean
    CommandLineRunner cargarDatos(
            UsuarioServicio usuarioServicio,
            InstitucionServicio institucionServicio,
            CursoServicio cursoServicio,
            EstudianteServicio estudianteServicio,
            CursoDocenteServicio cursoDocenteServicio,
            DocenteServicio docenteServicio) {

        return args -> {


            // Crear coordinador
            UsuarioModel coordinador = new UsuarioModel();
            coordinador.setNombre("pablito");
            coordinador.setCorreo("pablito@gmail.com");
            coordinador.setContraseña("atlas123".toCharArray());
            coordinador.setRol(Rol.COORDINADOR);

            usuarioServicio.registrarUsuario(coordinador);

            Long coordinadorId = coordinador.getId();

            // Crear institución
            institucionServicio.crearInstitucion(
                    "Institución Educativa ATLAS",
                    coordinadorId
            );

            // Crear docentes
            UsuarioModel docente1 = new UsuarioModel();
            docente1.setNombre("Carlos Rodríguez");
            docente1.setCorreo("carlos@gmail.com");
            docente1.setContraseña("atlas123".toCharArray());
            docente1.setRol(Rol.DOCENTE);

            usuarioServicio.registrarUsuario(docente1);

            UsuarioModel docente2 = new UsuarioModel();
            docente2.setNombre("María González");
            docente2.setCorreo("maria@gmail.com");
            docente2.setContraseña("atlas123".toCharArray());
            docente2.setRol(Rol.DOCENTE);

            usuarioServicio.registrarUsuario(docente2);

            // Vincular docentes a la institución
            docenteServicio.agregarPorCorreo(docente1.getCorreo(), coordinadorId);
            docenteServicio.agregarPorCorreo(docente2.getCorreo(), coordinadorId);

            Long docente1Id = docente1.getId();
            Long docente2Id = docente2.getId();

            // Crear cursos
            CursoModel curso1 = crearCurso("10", "A");
            CursoModel curso2 = crearCurso("10", "B");
            CursoModel curso3 = crearCurso("11", "A");

            cursoServicio.guardarCurso(curso1, coordinadorId);
            cursoServicio.guardarCurso(curso2, coordinadorId);
            cursoServicio.guardarCurso(curso3, coordinadorId);

            // 5. Asignar docentes a los cursos
            cursoDocenteServicio.asignar(
                    curso1.getId(), docente1Id,
                    "Docente de matematicas", coordinadorId);

            cursoDocenteServicio.asignar(
                    curso2.getId(), docente2Id,
                    "Docente de ingles", coordinadorId);

            cursoDocenteServicio.asignar(
                    curso3.getId(), docente1Id,
                    "Docente de filosofia", coordinadorId);


            //  Crear estudiantes
            // Curso 10-A
            crearEstudiante(
                    "Andrés Pérez", "Barrio El Recreo",
                    "Nueva EPS", "Carlos Pérez", "Ana Martínez",
                    "3001234567", "3011234567",
                    curso1, coordinadorId, estudianteServicio);

            crearEstudiante(
                    "Laura Martínez", "Barrio Manga",
                    "Sanitas", "Pedro Martínez", "Luisa Gómez",
                    "3021234567", "3031234567",
                    curso1, coordinadorId, estudianteServicio);

            crearEstudiante(
                    "Daniel Torres", "Barrio Crespo",
                    "Sura", "Jorge Torres", "Marta Ruiz",
                    "3041234567", "3051234567",
                    curso1, coordinadorId, estudianteServicio);

            // Curso 10-B
            crearEstudiante(
                    "Sofía Ramírez", "Barrio Bocagrande",
                    "Nueva EPS", "Luis Ramírez", "Claudia Pérez",
                    "3061234567", "3071234567",
                    curso2, coordinadorId, estudianteServicio);

            crearEstudiante(
                    "Mateo Herrera", "Barrio Manga",
                    "Sanitas", "Andrés Herrera", "Diana Castro",
                    "3081234567", "3091234567",
                    curso2, coordinadorId, estudianteServicio);

            crearEstudiante(
                    "Valentina Díaz", "Barrio El Recreo",
                    "Sura", "Miguel Díaz", "Laura Gómez",
                    "3101234567", "3111234567",
                    curso2, coordinadorId, estudianteServicio);

            // Curso 11-A
            crearEstudiante(
                    "Samuel Castro", "Barrio Crespo",
                    "Nueva EPS", "Carlos Castro", "Patricia López",
                    "3121234567", "3131234567",
                    curso3, coordinadorId, estudianteServicio);

            crearEstudiante(
                    "Isabella Moreno", "Barrio Manga",
                    "Sanitas", "Fernando Moreno", "Natalia Díaz",
                    "3141234567", "3151234567",
                    curso3, coordinadorId, estudianteServicio);

            crearEstudiante(
                    "Sebastián Rojas", "Barrio Bocagrande",
                    "Sura", "Ricardo Rojas", "María Torres",
                    "3161234567", "3171234567",
                    curso3, coordinadorId, estudianteServicio);
        };
    }

    private CursoModel crearCurso(String grado, String grupo) {
        CursoModel curso = new CursoModel();
        curso.setGrado(grado);
        curso.setGrupo(grupo);
        return curso;
    }

    private void crearEstudiante(
            String nombre,
            String direccion,
            String eps,
            String nombrePadre,
            String nombreMadre,
            String telefonoPadre,
            String telefonoMadre,
            CursoModel curso,
            Long coordinadorId,
            EstudianteServicio estudianteServicio) {

        EstudianteModel estudiante = new EstudianteModel();

        estudiante.setNombre(nombre);
        estudiante.setDireccion(direccion);
        estudiante.setEps_afiliada(eps);
        estudiante.setNombre_padre(nombrePadre);
        estudiante.setNombre_madre(nombreMadre);
        estudiante.setTelefono_padre(telefonoPadre);
        estudiante.setTelefono_madre(telefonoMadre);

        estudianteServicio.guardar(
                estudiante,
                curso.getId(),
                coordinadorId
        );
    }




}
