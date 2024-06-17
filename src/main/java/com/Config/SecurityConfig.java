package com.Config;

import com.response.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthProvider userAuth;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(userAuth), BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET, "/api/products/id/{productId}","/api/products/","/api/admin/products/all/","/api").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/products/creates/","/api/admin/products/","/api/admin/products/","api/getCart","/login","/register","api/createCart","/api/getCart").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/admin/products/{productId}/update","/api/cart/add","api/item/{cartItemId}").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/admin/products/{productId}/delete","api/item/{cartItemId}").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
