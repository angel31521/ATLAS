package com.example.atlas.modelo;

import java.util.ArrayList;
import java.util.List;

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


    @OneToMany(mappedBy = "curso")
    private List<EstudianteModel> estudiantes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "institucion_id", nullable = false)
    private InstitucionModel institucion;

    @OneToMany(mappedBy = "curso")
    private List<CursoDocenteModel> asignaciones = new ArrayList<>();


    public CursoModel() {}


    public CursoModel(Long id, String grado, String grupo, List<EstudianteModel> estudiantes,
            InstitucionModel institucion) {
        this.id = id;
        this.grado = grado;
        this.grupo = grupo;
        this.estudiantes = estudiantes;
        this.institucion = institucion;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getGrado() {
        return grado;
    }


    public void setGrado(String grado) {
        this.grado = grado;
    }


    public String getGrupo() {
        return grupo;
    }


    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }


    public List<EstudianteModel> getEstudiantes() {
        return estudiantes;
    }


    public void setEstudiantes(List<EstudianteModel> estudiantes) {
        this.estudiantes = estudiantes;
    }


    public InstitucionModel getInstitucion() {
        return institucion;
    }


    public void setInstitucion(InstitucionModel institucion) {
        this.institucion = institucion;
    }
    

    public List<CursoDocenteModel> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<CursoDocenteModel> asignaciones) {
        this.asignaciones = asignaciones;
    }

}
