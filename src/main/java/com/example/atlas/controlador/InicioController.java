package com.example.atlas.controlador;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/inicio")
public class InicioController {
    

    //accede a la vista principal
    @GetMapping 
    public String inicio(){

        return "inicio";
    }

    

    //espera un parametro llamado tipo
    @GetMapping("/login")
    public String login(

        @RequestParam (name = "tipo", required = false) String tipo , Model model){


        model.addAttribute("tipoUsuario", tipo);

        return "login";

    }



}
