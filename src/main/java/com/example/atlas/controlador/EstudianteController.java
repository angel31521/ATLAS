package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.servicio.EstudianteServicio;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.atlas.servicio.EstudiantePdfServicio;

@Controller
@RequestMapping("/panel/coordinador/estudiantes")
public class EstudianteController {

    private final EstudianteServicio estudianteServicio;
    private final EstudiantePdfServicio estudiantePdfServicio;

    public EstudianteController(EstudianteServicio estudianteServicio,
                                EstudiantePdfServicio estudiantePdfServicio) {
        this.estudianteServicio = estudianteServicio;
        this.estudiantePdfServicio = estudiantePdfServicio;
    }

    // Guardar estudiante nuevo
    @PostMapping
    public String guardar(@RequestParam Long cursoId,
                          @ModelAttribute EstudianteModel estudiante,
                          HttpSession session,
                          RedirectAttributes ra) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        try {
            estudianteServicio.guardar(estudiante, cursoId, coordinadorId);
            ra.addFlashAttribute("mensajeExito", "Estudiante agregado correctamente.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorEstudiante", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }

    // Guardar cambios de la edición
    @PostMapping("/{id}/editar")
    public String editar(@PathVariable Long id,
                         @ModelAttribute EstudianteModel datos,
                         HttpSession session,
                         RedirectAttributes ra) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        try {
            estudianteServicio.actualizar(id, datos, coordinadorId);
            ra.addFlashAttribute("mensajeExito", "Estudiante actualizado correctamente.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorEstudiante", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id, HttpSession session) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");
        if (coordinadorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        EstudianteModel estudiante;
        try {
            
            estudiante = estudianteServicio.buscarDelCoordinador(id, coordinadorId);
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