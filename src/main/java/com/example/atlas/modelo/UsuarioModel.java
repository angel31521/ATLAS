package com.example.atlas.modelo;

import com.example.atlas.enums.Rol;
import jakarta.persistence.*;


@Entity
public class UsuarioModel {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(nullable = false, length = 100)
 private  String nombre;

 @Column(nullable = false)
 private String correo;


 //char guarda solo un caracter 
 // pero al hacer un array de char guarda cada carcater de la contraseña [c,s,6,*,/]
 @Column( nullable = false)
 private char[] contraseña;


 @Enumerated(EnumType.STRING)
 private Rol rol;

 
 //institucion_id (FK)



 public UsuarioModel() {
 }

 public UsuarioModel(Long id, String nombre, String correo, char[] contraseña, Rol rol) {
    this.id = id;
    this.nombre = nombre;
    this.correo = correo;
    this.contraseña = contraseña;
    this.rol = rol;
 }

 public Long getId() {return id;}
 public void setId(Long id) {  this.id = id; }


 public String getNombre() {return nombre;}
 public void setNombre(String nombre) {this.nombre = nombre;}


 public String getCorreo() {return correo;}
 public void setCorreo(String correo) {this.correo = correo;}


 public char[] getContraseña() {return contraseña;}
 public void setContraseña(char[] contraseña) {this.contraseña = contraseña;}


 public Rol getRol() {return rol;}
 public void setRol(Rol rol) {this.rol = rol;}





 







}
