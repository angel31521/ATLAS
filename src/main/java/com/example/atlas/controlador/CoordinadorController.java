package com.example.atlas.controlador;

import org.springframework.stereotype.*;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel") 
public class CoordinadorController {

    @GetMapping("/coordinador")
    public String panelCoordinador(){


        return "coordinador/panel-coordinador";

    }

}
