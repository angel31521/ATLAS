package com.example.atlas.modelo;

import jakarta.persistence.*;

@Entity 
public class CursoModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15 )
    private String grado;

    @Column(nullable = false, length = 15)
    private String grupo;


    //Instirucion_id (FK)


    public CursoModel() {}


    public CursoModel(Long id, String grado, String grupo) {
        this.id = id;
        this.grado = grado;
        this.grupo = grupo;
    }


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}


    public String getGrado() {return grado;}
    public void setGrado(String grado) {this.grado = grado;}


    public String getGrupo() {return grupo;}
    public void setGrupo(String grupo) { this.grupo = grupo;}

    




    





}
