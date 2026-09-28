package com.example.atlas.modelo;

import jakarta.persistence.*;



@Entity
public class InstitucionModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private char[] codigoInvitacion;





    public InstitucionModel() {}

    public InstitucionModel(Long id, String nombre, char[] codigoInvitacion) {
        this.id = id;
        this.nombre = nombre;
        this.codigoInvitacion = codigoInvitacion;
    }

    

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public char[] getCodigoInvitacion() {return codigoInvitacion;}
    public void setCodigoInvitacion(char[] codigoInvitacion) {this.codigoInvitacion = codigoInvitacion;}


    




}
