package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorServicio autorServ;
    @Autowired
    private AutorRepositorio autorRepos;

    @GetMapping("/administrar")
    public String formulario(ModelMap modelo){

        List<Autor> autores = autorRepos.findAll();

        modelo.put("autores", autores);

        return "autor.html";
    }
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo,@RequestParam String nombre) throws errorServicio{
        try{

            autorServ.registrar(nombre);

        }catch(errorServicio e){

            modelo.put("error", e.getMessage());
            modelo.put("nombre",nombre);

            List<Autor> autores = autorRepos.findAll();
            modelo.put("autores", autores);

            return "index.html";
        }
        modelo.put("exito", "El autor fue registrado de manera satisfactoria!");

        return "index.html";
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws errorServicio{
        try{
            autorServ.desabilitar(id);
            return "redirect:/autor/administrar";
        }catch(Exception e){
            return "redirect:/";  
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws errorServicio{
        try{
            autorServ.habilitar(id);
            return "redirect:/autor/administrar";
        }catch(Exception e){
            return "redirect:/";
        }
    }
}