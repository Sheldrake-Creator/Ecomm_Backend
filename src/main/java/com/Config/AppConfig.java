package com.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.service", "com.mapper", "com.dto", "com.model", "com.Config", "com.controller",
        "com.exception", "com.request", "com.response", "com.repository", "com.util" })
public class AppConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http)throws
    // Exception{
    //
    // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests
    // (Authorize
    // ->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
    // .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
    // .csrf().disable()
    // .cors().configurationSource(new CorsConfigurationSource() {
    // @Override
    // public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
    //
    // CorsConfiguration cfg=new CorsConfiguration();
    //
    // cfg.setAllowedOrigins(Arrays.asList(
    // "http://localhost:4545",
    // "http://localhost:4200/"
    // ));
    // cfg.setAllowedMethods(Collections.singletonList("*"));
    // cfg.setAllowCredentials(true);
    // cfg.setAllowedHeaders(Collections.singletonList("*"));
    // cfg.setExposedHeaders(Arrays.asList("Authorization"))
    // ;
    // cfg.setMaxAge(3600L);
    // return cfg;
    //
    // }
    // })
    // .and().httpBasic().and().formLogin();
    // return http.build();
    // }
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }
}
