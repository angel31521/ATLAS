package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/panel")
public class DocenteController {


    @GetMapping("/docente")
    public String panelDocente(){


        return "/docente/panel-docente";

    }

}
