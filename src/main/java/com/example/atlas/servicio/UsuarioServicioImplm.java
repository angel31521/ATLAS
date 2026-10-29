package com.example.atlas.servicio;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.UsuarioRepositorio;

@Service 
public class UsuarioServicioImplm implements UsuarioServicio {



    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicioImplm(UsuarioRepositorio usuarioRepositorio){

        this.usuarioRepositorio = usuarioRepositorio;

    }



    //Valida el rol, lo convierte en enum y lo asigna a la entidad
    @Override 
    public UsuarioModel crearUsuarioRol(String tipo){

       

        if(!tipo.equalsIgnoreCase("COORDINADOR") && !tipo.equalsIgnoreCase("DOCENTE") ){
        
        throw new IllegalArgumentException("Rol no válido");};


        UsuarioModel usuarioModel = new UsuarioModel();
        Rol rol = Rol.valueOf(tipo.toUpperCase());
        usuarioModel.setRol(rol);



        return usuarioModel;

    }


    //Registra el usaurio en la base de datos H2 usando el metodo .save() de JpaRepository
    //valiar si el usaurio ya existe
    @Override 
    public void registrarUsuario(UsuarioModel usuario){

        usuarioRepositorio.save(usuario);

    }


    //busca por correo y maneja el optional 
    //revisar como manejar la contraseña de forma segura
    @Override
    public UsuarioModel validarLogin(String correo, char[] contraseña) {
  

        UsuarioModel usuario = usuarioRepositorio.findByCorreo(correo).orElse(null);

        if(usuario == null){
            return null;
        }

        if(!Arrays.equals(usuario.getContraseña(), contraseña)){
            return null;
        }

        return usuario;
    }



    @Override
    public UsuarioModel buscarPorId(Long id) {
        return usuarioRepositorio.findById(id).orElseThrow(() -> 
                                    new IllegalArgumentException("Usuario no encontrado") )  ;
    }



    @Override
    public boolean tieneInstitucion(Long usuarioId) {

            UsuarioModel usuario = buscarPorId(usuarioId);

            return usuario != null && usuario.getInstitucion() != null;
        
    }










}
