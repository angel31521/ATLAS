package com.example.atlas.controlador;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.servicio.InstitucionServicio;
import com.example.atlas.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel/coordinador") 
public class CoordinadorController {


    private final UsuarioServicio usuarioServicio;
    private final InstitucionServicio institucionServicio;

    public CoordinadorController(UsuarioServicio usuarioServicio, 
                                InstitucionServicio institucionServicio){

        this.usuarioServicio = usuarioServicio;
        this.institucionServicio = institucionServicio;
    }




    //PONER LA LOGICA DENTRO DE UN METODO EN EL SERVICIO(EL CONTROLADOR ESTA MUY LLENO)
    @GetMapping
    public String panelCoordinador(HttpSession session, Model model){

        Long usuarioId = (Long) session.getAttribute("usuarioId");

        boolean tieneInstitucion = usuarioServicio.tieneInstitucion(usuarioId);

         UsuarioModel coordinador = usuarioServicio.buscarPorId(usuarioId);

         InstitucionModel institucion = coordinador.getInstitucion();

    

    model.addAttribute("mostrarFormulario", !tieneInstitucion);

    
    if (!tieneInstitucion) {
        institucion = new InstitucionModel();
    }

    model.addAttribute("institucion", institucion);

        return "coordinador/panel-coordinador";

    }



    @PostMapping
    public String formularioCoordinador(Model model, InstitucionModel institucion,
                                        @RequestParam(name="nombre") String nombre, HttpSession session ){


    model.addAttribute("institucion", institucion );
    Long coordinadorId = (Long) session.getAttribute("usuarioId");
    institucionServicio.crearInstitucion(nombre,coordinadorId);


    return "redirect:/panel/coordinador";
    
    }

}
