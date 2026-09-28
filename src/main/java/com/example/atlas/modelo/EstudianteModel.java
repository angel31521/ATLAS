package com.example.atlas.modelo;

import jakarta.persistence.*;

@Entity 
public class EstudianteModel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String direccion;
    private String eps_afiliada;
    private String nombre_padre;
    private String nombre_madre;
    private String telefono_padre;
    private String telefono_madre;

    public EstudianteModel() {}

    
    public EstudianteModel(Long id, String nombre, String direccion, String eps_afiliada, String nombre_padre,
            String nombre_madre, String telefono_padre, String telefono_madre) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.eps_afiliada = eps_afiliada;
        this.nombre_padre = nombre_padre;
        this.nombre_madre = nombre_madre;
        this.telefono_padre = telefono_padre;
        this.telefono_madre = telefono_madre;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getEps_afiliada() {return eps_afiliada;}
    public void setEps_afiliada(String eps_afiliada) {this.eps_afiliada = eps_afiliada;}

    public String getNombre_padre() {return nombre_padre;}
    public void setNombre_padre(String nombre_padre) {this.nombre_padre = nombre_padre;}

    public String getNombre_madre() {return nombre_madre;}
    public void setNombre_madre(String nombre_madre) {this.nombre_madre = nombre_madre;}

    public String getTelefono_padre() {return telefono_padre;}
    public void setTelefono_padre(String telefono_padre) {this.telefono_padre = telefono_padre;}

    public String getTelefono_madre() {return telefono_madre;}
    public void setTelefono_madre(String telefono_madre) {this.telefono_madre = telefono_madre;}


    
    


}
