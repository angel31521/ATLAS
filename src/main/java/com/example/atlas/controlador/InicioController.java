package com.example.atlas.controlador;
import com.example.atlas.servicio.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.atlas.enums.Rol;
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


        //recibe el formulario y hace la logica de asignacion
        //pd: no se que tan seguro sea mandar la contraseña asi, asi que no la use, la dejo por si acaso (REVISAR)
        @PostMapping("/login")
        public String login(@RequestParam (name = "tipo", required = false) String tipo ,
                        @RequestParam (name = "email") String correo,
                        @RequestParam (name = "password") char[] contraseña, Model model){

        model.addAttribute("tipoUsuario", tipo);

        UsuarioModel usuario = usuarioServicio.validarLogin(correo, contraseña);

        if(usuario == null){

            model.addAttribute("error", "Correo o contraseña incorrectos");

            return "login";
        }

        if(usuario.getRol() == Rol.COORDINADOR ){
            return "redirect:/panel/coordinador";
        }

        if(usuario.getRol() == Rol.DOCENTE){
            return "redirect:/panel/docente";
        }

        
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


        return "redirect:/inicio/login?tipo=" + usuario.getRol().name();

    }



}
