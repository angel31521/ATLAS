package com.example.atlas.servicio;

import org.springframework.stereotype.Service;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.UsuarioRepositorio;


@Service 
public class UsuarioServicio {


 private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio){

        this.usuarioRepositorio = usuarioRepositorio;

    }



    //Valida el rol, lo convierte en enum y lo asigna a la entidad
    public UsuarioModel crearUsuarioRol(String tipo){

       

        if(!tipo.equalsIgnoreCase("COORDINADOR") && !tipo.equalsIgnoreCase("DOCENTE") ){
        
        throw new IllegalArgumentException("Rol no válido");};


        UsuarioModel usuarioModel = new UsuarioModel();
        Rol rol = Rol.valueOf(tipo.toUpperCase());
        usuarioModel.setRol(rol);



        return usuarioModel;

    }


    //Registra el usaurio en la base de datos H2 usando el metodo .save() de CrudRepository 
    public void registrarUsuario(UsuarioModel usuario){

        usuarioRepositorio.save(usuario);

    }

    



}
