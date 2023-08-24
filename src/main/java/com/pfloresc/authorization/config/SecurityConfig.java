package com.pfloresc.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
public class SecurityConfig {

    //Endpoint level authorrization
    // macher
    // 1. AnyRequest
    // 2. RequestMatchers
    // 3. RequestMatchers with HttpMethod

    //---Authorization rule
    // 1. PermitAll
    // 2. DenyAll
    // 3. Authenticated
    // 4. HasRole
    // 5. HasAuthority
    // 6. Acces(SpEL) - spring expresion language
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
//                .requestMatchers("/demo").permitAll()
//                .requestMatchers("/admin").hasRole("ADMIN")
//                .requestMatchers("/dba").hasAnyAuthority("DBA","ADMIN ")
                .requestMatchers(HttpMethod.POST, "/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/add").authenticated()
                .and().csrf().disable().build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http
//                .httpBasic()
//                .and()
//                .authorizeHttpRequests()
//                //.anyRequest().permitAll() //permite casi todas las peticiones, solo rechaza las realizadas por usuarios que no esten en userDetail
//                //.anyRequest().denyAll() // Bloquea todas las peticiones
//                //.anyRequest().authenticated() //Solo las peticiones autorizadas en el UserDetails
//                .anyRequest().hasRole("ADMIN")
//                .anyRequest().hasAuthority("write") // funciona igual que hasRole pero con sobre los permisos de
//                .anyRequest().access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('DBA')")) //Spring Expression Language
//                .and().build();
//    }
    @Bean
    public UserDetailsService userDetailsService(){

        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(passwordEncoder().encode("password123"))
                        .authorities("read", "ROLE_USER")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder().encode("password123"))
                        .authorities("read","write", "ROLE_ADMIN")
                        .build(),
                User.withUsername("dba")
                        .password(passwordEncoder().encode("password123"))
                        .authorities("read", "ROLE_DBA")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
