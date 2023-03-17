package com.Portfolio.myPortfolio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
public class MainSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
            .authorizeRequests()
            .antMatchers("*").permitAll() // Rutas públicas sin autenticación
            //.antMatchers("/login", "/personas/ver", "/habilidades/ver", "/proyectos/ver", "habilidades/editar", "/educacion/ver").permitAll() // Rutas públicas sin autenticación
            //.antMatchers("/personas/**", "/habilidades/**", "/proyectos/**", "/educacion/**").authenticated() // Rutas autenticadas
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf()
            .disable()
            .build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("https://lourdes-ramos-6b55b.web.app/");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
    }
}