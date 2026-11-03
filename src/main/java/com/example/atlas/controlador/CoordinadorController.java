package com.example.atlas.controlador;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.servicio.CursoServicio;
import com.example.atlas.servicio.DocenteServicio;
import com.example.atlas.servicio.EstudianteServicio;
import com.example.atlas.servicio.InstitucionServicio;
import com.example.atlas.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel/coordinador")
public class CoordinadorController {

    private final UsuarioServicio usuarioServicio;
    private final InstitucionServicio institucionServicio;
    private final CursoServicio cursoServicio;
    private final EstudianteServicio estudianteServicio;
    private final DocenteServicio docenteServicio;

    public CoordinadorController(UsuarioServicio usuarioServicio,
                                InstitucionServicio institucionServicio,
                                CursoServicio cursoServicio,
                                EstudianteServicio estudianteServicio,
                                DocenteServicio docenteServicio) {
        this.usuarioServicio = usuarioServicio;
        this.institucionServicio = institucionServicio;
        this.cursoServicio = cursoServicio;
        this.estudianteServicio = estudianteServicio;
        this.docenteServicio = docenteServicio;
    }

    // PONER LA LOGICA DENTRO DE UN METODO EN EL SERVICIO (EL CONTROLADOR ESTA MUY LLENO)
    @GetMapping
    public String panelCoordinador(HttpSession session, Model model) {

        Long usuarioId = (Long) session.getAttribute("usuarioId");

        boolean tieneInstitucion = usuarioServicio.tieneInstitucion(usuarioId);
        UsuarioModel coordinador = usuarioServicio.buscarPorId(usuarioId);
        InstitucionModel institucion = coordinador.getInstitucion();

        model.addAttribute("mostrarFormulario", !tieneInstitucion);

        if (!tieneInstitucion) {
            institucion = new InstitucionModel();
        }
        model.addAttribute("institucion", institucion);

        // Listar cursos
        model.addAttribute("cursos", cursoServicio.listarCursosPorCoordinador(usuarioId));

        // Listar estudiantes
        model.addAttribute("estudiantes", estudianteServicio.listarPorCoordinador(usuarioId));

        model.addAttribute("docentes", docenteServicio.listarPorCoordinador(usuarioId));

        return "coordinador/panel-coordinador";
    }

    @PostMapping
    public String formularioCoordinador(Model model, InstitucionModel institucion,
                                        @RequestParam(name = "nombre") String nombre,
                                        HttpSession session) {

        model.addAttribute("institucion", institucion);
        Long coordinadorId = (Long) session.getAttribute("usuarioId");
        institucionServicio.crearInstitucion(nombre, coordinadorId);

        return "redirect:/panel/coordinador";
    }

    @PostMapping("/cursos")
    public String guardarCurso(@RequestParam String grado, @RequestParam String grupo,
                               HttpSession session, RedirectAttributes redirectAttributes) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        CursoModel curso = new CursoModel();
        curso.setGrado(grado);
        curso.setGrupo(grupo);

        try {
            cursoServicio.guardarCurso(curso, coordinadorId);
            redirectAttributes.addFlashAttribute("mensajeExito", "Curso registrado correctamente.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorCurso", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }
}