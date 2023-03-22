package com.Portfolio.myPortfolio.controller;

import com.Portfolio.myPortfolio.model.Educacion;
import com.Portfolio.myPortfolio.model.Exp_Laboral;
import com.Portfolio.myPortfolio.model.Habilidad;
import com.Portfolio.myPortfolio.model.Persona;
import com.Portfolio.myPortfolio.model.Proyecto;
import com.Portfolio.myPortfolio.security.User;
import com.Portfolio.myPortfolio.security.UserDetails;
import com.Portfolio.myPortfolio.service.IEducacionService;
import com.Portfolio.myPortfolio.service.IExpService;
import com.Portfolio.myPortfolio.service.IHabilidadService;
import com.Portfolio.myPortfolio.service.IPersonaService;
import com.Portfolio.myPortfolio.service.IProyectoService;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin ("https://lourdes-ramos-171717.web.app/")
//@CrossOrigin ("http://localhost:4200")
@RestController
public class Controller {
    
    @Autowired
    private IPersonaService persoServ;
    @Autowired
    private IProyectoService proyeServ;
    @Autowired
    private IExpService expServ;
    @Autowired
    private IHabilidadService habServ;
    @Autowired
    private IEducacionService eduServ;
    @Autowired
    private UserDetails userService;
    
    @GetMapping
    public String hola(){
        return "This is working, from Lya.";
    }
    
        @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody User loginRequest) {
        try {
            String email = loginRequest.getEmail();
            org.springframework.security.core.userdetails.UserDetails userDetails = userService.loadUserByUsername(email);
            String encodedPassword = userDetails.getPassword();
        if (!passwordEncoder().matches(loginRequest.getPassword(), encodedPassword)) {
            throw new BadCredentialsException("Invalid username or password");
        }
            User user = new User(userDetails.getUsername(), encodedPassword, userDetails.getAuthorities());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Login successful");
        return ResponseEntity.ok().body(jsonResponse.toString());
            } catch (BadCredentialsException e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Login successful");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse.toString());
      }
    }
    
    //-------------------------------------------------------------------------
    //PERSONA
    //-------------------------------------------------------------------------
    
    @GetMapping ("/personas/ver")
    @ResponseBody
    public List<Persona> verPersonas(){
        return persoServ.verPersonas();
    }
    
    @PostMapping ("/personas/new")
    public void agregarPersona(@RequestBody Persona per){
        persoServ.crearPersonas(per);
    }

    @DeleteMapping ("/personas/delete/{id}")
    public void borrarPersona(@PathVariable Long id){
        persoServ.borrarPersona(id);
    }
    
    @PutMapping ("/personas/editar/{id}")
    public Persona guardarPersona(@PathVariable Long id,
            @RequestParam ("nombre") String nuevoNombre,
            @RequestParam ("apellido") String nuevoApellido,
            @RequestParam ("sobre_mi") String nuevoSobreMi,
            @RequestParam ("url_perfil") String nuevoPerfil,
            @RequestParam ("url_portada") String nuevaPortada,
            @RequestParam ("url_correo") String nuevoCorreo,
            @RequestParam ("url_github") String nuevoGithub){
        Persona person = persoServ.buscarPersonas(id);
        person.setNombre(nuevoNombre);
        person.setApellido(nuevoApellido);
        person.setSobre_mi(nuevoSobreMi);
        person.setUrl_perfil(nuevoPerfil);
        person.setUrl_portada(nuevaPortada);
        person.setUrl_correo(nuevoCorreo);
        person.setUrl_github(nuevoGithub);
        persoServ.crearPersonas(person);
        return person;
    }
    
    //-------------------------------------------------------------------------
    //PROYECTO
    //-------------------------------------------------------------------------
    
    @GetMapping ("/proyectos/ver")
    @ResponseBody
    public List<Proyecto> verProyectos(){
        return proyeServ.verProyecto();
    }
    
    @PostMapping ("/proyectos/new")
    public void crearProyecto(@RequestBody Proyecto pro){
        proyeServ.guardarProyecto(pro);
    }
    
    @DeleteMapping ("/proyectos/delete/{id}")
    public void borrarProyecto(@PathVariable Long id){
        proyeServ.borrarProyecto(id);
    }
    
    @PutMapping ("/proyectos/editar/{id}")
    public Proyecto guardarProyectos(@PathVariable Long id,
            @RequestParam ("titulo") String nuevoTitulo,
            @RequestParam ("descripcion") String nuevaDescripcion,
            @RequestParam ("image") String nuevaImagen){
        
        Proyecto proyect = proyeServ.buscarProyectos(id);
        proyect.setTitulo(nuevoTitulo);
        proyect.setDescripcion(nuevaDescripcion);
        proyect.setImage(nuevaImagen);
        proyeServ.guardarProyecto(proyect);
        return proyect;
    }

    //-------------------------------------------------------------------------
    //EXP_LABORAL
    //-------------------------------------------------------------------------
    
    @GetMapping ("/experiencias/ver")
    @ResponseBody
    public List<Exp_Laboral> verExperiencias(){
        return expServ.verExperiencias();
    }
    
    @PostMapping ("/experiencias/new")
    public void guardarExperiencia(@RequestBody Exp_Laboral exp){
        expServ.guardarExp(exp);
    }
    
    @DeleteMapping ("/experiencias/delete/{id}")
    public void borrarExperiencia(@PathVariable Long id){
        expServ.borrarExp(id);
    }
    
    @PutMapping ("/experiencias/editar/{id}")
    public Exp_Laboral editarExperiencia(@PathVariable Long id,
                @RequestParam ("empresa") String nuevaEmpresa,
                @RequestParam ("cargo") String nuevoCargo,
                @RequestParam ("fecha_inicio") String nuevaFechaInicio,
                @RequestParam ("fecha_fin") String nuevafechaFin,
                @RequestParam ("descripcion") String nuevadescripcion){
        
        Exp_Laboral expL = expServ.buscarExp(id);
        expL.setEmpresa(nuevaEmpresa);
        expL.setCargo(nuevoCargo);
        expL.setFecha_inicio(nuevaFechaInicio);
        expL.setFecha_fin(nuevafechaFin);
        expL.setDescripcion(nuevadescripcion);
        expServ.guardarExp(expL);
        return expL;
    }
    
    //-------------------------------------------------------------------------
    //HABILIDAD
    //-------------------------------------------------------------------------
    
    @GetMapping ("/habilidades/ver")
    @ResponseBody
    public List<Habilidad> verHabilidades(){
        return habServ.verHabilidades();
    }
    
    @PostMapping ("/habilidades/new")
    public void guardarHabilidad(@RequestBody Habilidad hab){
        habServ.guardarHabilidad(hab);
    }
    
    @DeleteMapping ("habilidades/delete/{id}")
    public void borrarHabilidad(@PathVariable Long id){
        habServ.borrarHabilidad(id);
    }
    
    @PutMapping ("/habilidades/editar/{id}")
    public Habilidad editarHabilidad(@PathVariable Long id,
            @RequestParam ("habilidad") String nuevaHabilidad,
            @RequestParam ("nivel") int nuevoNivel){
        Habilidad habil = habServ.buscarHabilidad(id);
        habil.setHabilidad(nuevaHabilidad);
        habil.setNivel(nuevoNivel);
        habServ.guardarHabilidad(habil);
        return habil;
    }
    
    //-------------------------------------------------------------------------
    //EDUCATION
    //-------------------------------------------------------------------------
    
    @GetMapping ("/educacion/ver")
    @ResponseBody
    public List<Educacion> verEducacion(){
        return eduServ.verEducacion();
    }
    
    @PostMapping ("/educacion/new")
    public void guardarEducacion(@RequestBody Educacion edu){
        eduServ.guardarEducacion(edu);
    }
    
    @DeleteMapping ("/educacion/delete/{id}")
    public void borrarEducacion(@PathVariable Long id){
        eduServ.borrarEducacion(id);
    }
    
    @PutMapping ("/educacion/editar/{id}")
    public Educacion editarEducacion(@PathVariable Long id,
            @RequestParam ("institucion") String nuevaInstitucion,
            @RequestParam ("titulo") String nuevoTitulo,
            @RequestParam ("descripcion") String nuevaDescripcion){
        
        Educacion educ = eduServ.buscarEducacion(id);
        educ.setInstitucion(nuevaInstitucion);
        educ.setTitulo(nuevoTitulo);
        educ.setDescripcion(nuevaDescripcion);
        eduServ.guardarEducacion(educ);
        return educ;
    }
}