package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.atlas.modelo.EstudianteModel;
import com.example.atlas.servicio.EstudianteServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel/coordinador/estudiantes")
public class EstudianteController {

    private final EstudianteServicio estudianteServicio;

    public EstudianteController(EstudianteServicio estudianteServicio) {
        this.estudianteServicio = estudianteServicio;
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
}