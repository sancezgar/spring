package mx.com.gm.web;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import mx.com.gm.domain.Persona;
import mx.com.gm.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private PersonaService ps;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        List<Persona> personas = (List<Persona>) ps.listarPersonas();
        Double saldoTotal = 0D;
        
        for(Persona p: personas){
            saldoTotal += p.getSaldo();
        }
        
        log.info("ejecutando el controlador Spring MVC");
        log.info("Usuario que ha echo login: " + user);
        model.addAttribute("personas", personas);
        model.addAttribute("user", user);
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());
        
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona) {
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Validated Persona persona, BindingResult errors) {
        
        if(errors.hasErrors()){
            return "modificar";
        }
        
        ps.guardar(persona);
        return "redirect:/";
    }
    
    @GetMapping("/editar/{idPersona}")
    public String editar(Persona persona, Model model){
        persona = ps.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";
    }
    
    @GetMapping("/eliminar")
    public String eliminar(Persona persona) {
        ps.eliminar(persona);
        return "redirect:/";
    }
}
