package com.pack.seproject.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pack.seproject.controller.CustomUserDetailsController;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    UserDetailsService userDetailsService(){
        return new CustomUserDetailsController();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/images/**", "/css/**", "/js/**", "/pages/**").permitAll()
                                                .requestMatchers("/userhome").authenticated()
                                                .requestMatchers("/signin", "/signin_process").permitAll()
                                                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .loginProcessingUrl("/home")
                                        .defaultSuccessUrl("/userhome")
                                        .permitAll())
                .logout(logout -> logout.permitAll())
                                                
                .build();
    }
}
