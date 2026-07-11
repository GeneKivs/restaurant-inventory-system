package com.restaurant.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//mport org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.restaurant.inventory.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
     @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService; // Use CustomUserDetailsService instead of in-memory
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/dashboard").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/forgotPassword","/resetPassword").permitAll()
                .requestMatchers("/inventory/**").hasAuthority("Inventory")
                .requestMatchers("/purchases/**").hasAuthority("Purchase")
                .requestMatchers("/admin/**").hasAuthority("Admins")
                .requestMatchers("/report/**").hasAuthority("Reports")
                .requestMatchers("/profile/**").permitAll()
                
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")  // Set custom login page
                .loginProcessingUrl("/perform-login") // URL for form submission
                .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                .failureUrl("/login?error=true") // Redirect on login failure
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
            .invalidSessionUrl("/login?sessionExpired=true")  // Redirect if session expires
            .sessionFixation().newSession()  // Prevent session fixation attacks
            .maximumSessions(1)  // Allow only one session per user
            .expiredUrl("/login?sessionExpired=true")  // Redirect if session expires
            )

            .csrf(csrf -> csrf.disable())
            .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

   /*@Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/").permitAll();
                    registry.requestMatchers("/inventory").hasRole("ADMIN");
                    registry.anyRequest().authenticated();
        })
            .formLogin(formLogin -> formLogin.permitAll())
            .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails adminUser = User.builder()
                        .username("admin")
                        .password("$2a$12$iXOkkRO/vvbAYN.7X/UjAuZoUQg88.Bu4e3oXh/tJUIdfXVZX7Ngy")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(adminUser);                
    } */ 
    

    
     

    
}
