package com.restaurant.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    
    
     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeHttpRequests(auth -> auth
                                 // Define role-based access for each module
                                 .requestMatchers("/dashboard/**").hasAuthority("Dashboard")
                                 .requestMatchers("/inventory/**").hasAuthority("Inventory")
                                 .requestMatchers("/purchase/**").hasAuthority("Purchase")
                                 .anyRequest().authenticated() // All other requests require authentication
                 )
                 .formLogin(withDefaults());
                 // Enable logout functionality

        return http.build();
    }

    private Customizer<FormLoginConfigurer<HttpSecurity>> withDefaults() {
        
        throw new UnsupportedOperationException("Unimplemented method 'withDefaults'");
    }
}
