package com.example.atlas.servicio;

import java.util.List;

public interface CursoService {

    public void agregar(String curso, String grupo);

    public String consultar(Long id,String curso, String grupo);

    public String elminar(long id);

    public List<String> listar();


}
