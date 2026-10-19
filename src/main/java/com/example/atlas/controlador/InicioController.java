package com.example.atlas.controlador;
import com.example.atlas.servicio.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.atlas.modelo.UsuarioModel;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/inicio")
public class InicioController {
    

    private final UsuarioServicio usuarioServicio;


    InicioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }



    //accede a la vista principal
    @GetMapping 
    public String inicio(){

        return "inicio";
    }

    

    //espera un parametro llamado tipo
    @GetMapping("/login")
    public String login(@RequestParam (name = "tipo", required = false) String tipo , Model model){


        model.addAttribute("tipoUsuario", tipo);

        return "login";

    }

    //Carga el formulario con el objeto vacio
    @GetMapping("/signup")
    public String signup(@RequestParam (name = "tipo", required = false) String tipo, UsuarioModel usuario, Model model){

        

        model.addAttribute("tipoUsuario", tipo);
        model.addAttribute("usuario", usuarioServicio.crearUsuarioRol(tipo));

        return "signup";

    }



    //entra el objeto con datos llenos y registra el usuario en la base de datos 
    @PostMapping("/signup")
    public String registrar(@ModelAttribute UsuarioModel usuario, Model model){

       

        usuarioServicio.registrarUsuario(usuario);

        model.addAttribute("usaurio", usuario);


        return "redirect:/inicio/login";


    }



}
