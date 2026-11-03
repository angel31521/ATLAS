package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.servicio.DocenteServicio;
import com.example.atlas.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.servicio.EstudiantePdfServicio;

@Controller
@RequestMapping("/panel/docente")
public class DocenteController {
    private final UsuarioServicio usuarioServicio;
    private final DocenteServicio docenteServicio;
    private final EstudiantePdfServicio estudiantePdfServicio;

    public DocenteController(UsuarioServicio usuarioServicio,
                             DocenteServicio docenteServicio,
                             EstudiantePdfServicio estudiantePdfServicio) {
        this.usuarioServicio = usuarioServicio;
        this.docenteServicio = docenteServicio;
        this.estudiantePdfServicio = estudiantePdfServicio;
    }

    @GetMapping
    public String panelDocente(HttpSession session, Model model) {

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }

        UsuarioModel docente = usuarioServicio.buscarPorId(usuarioId);
        if (docente.getRol() != Rol.DOCENTE) {
            return "redirect:/login";
        }

        model.addAttribute("docente", docente);
        model.addAttribute("institucion", docente.getInstitucion());
        model.addAttribute("cursos", docenteServicio.cursosDelDocente(usuarioId));
        model.addAttribute("estudiantes", docenteServicio.estudiantesDelDocente(usuarioId));

        return "docente/panel-docente";
    }

        @GetMapping("/estudiantes/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id, HttpSession session) {

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UsuarioModel docente = usuarioServicio.buscarPorId(usuarioId);
        if (docente.getRol() != Rol.DOCENTE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        EstudianteModel estudiante;
        try {
            estudiante = docenteServicio.buscarDelDocente(id, usuarioId);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        byte[] pdf = estudiantePdfServicio.generar(estudiante);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"estudiante-" + id + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}