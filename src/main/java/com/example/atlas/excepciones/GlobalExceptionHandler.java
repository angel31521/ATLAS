package com.example.atlas.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice 
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String manejarErrorGeneral(Exception ex, Model model) {

        model.addAttribute("mensaje",
                "Ha ocurrido un error inesperado.");

        return "errores/500";
    }




    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String manejarError404(Model model) {

        model.addAttribute("mensaje",
                "La página que buscas no existe.");

        return "errores/404";
}










}
