package com.Portfolio.myPortfolio.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Basic
    private String nombre;
    private String apellido;
    private String sobre_mi;
    private String url_perfil;
    private String url_portada;
    private String url_correo;
    private String url_github;

    public Persona() {}

    public Persona(Long id, String nombre, String apellido, String sobre_mi, String url_perfil, String url_portada, String url_correo, String url_github) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sobre_mi = sobre_mi;
        this.url_perfil = url_perfil;
        this.url_portada = url_portada;
        this.url_correo = url_correo;
        this.url_github = url_github;
    }

    
    
}
