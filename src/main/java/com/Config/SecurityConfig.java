package com.config;

import com.response.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final UserAuthProvider userAuth;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .addFilterBefore(new JwtAuthFilter(userAuth), BasicAuthenticationFilter.class)
                                .sessionManagement(customizer -> customizer
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                                .authorizeHttpRequests((requests) -> requests.requestMatchers(HttpMethod.GET,
                                                "/api/users/test", "/api/orders/{orderId}",
                                                "/api/reviews/product/{productId}", "/api/ratings/product/{productId}",
                                                "/api/ratings/product/{productId}", "/api/users/id", "/api/orders/user",
                                                "/api/admin/orders/{orderId}", "/api/products/id/{productId}",
                                                "/api/admin/orders", "/api/admin/orders/{orderId}", "api/orders/user",
                                                "api/getCart", "/api/products/id/{productId}", "/api/products/",
                                                "/api/admin/products/all", "/api").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/orders/add",
                                                                "/api/reviews/create", "/api/ratings/create",
                                                                "/api/orders", "/api/admin/products/creates",
                                                                "/api/admin/products/", "api/getCart", "/login",
                                                                "/register", "api/createCart", "/api/getCart",
                                                                "/api/address/add")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/api/item/{cartItemId}",
                                                                "/api/item/add", "/api/admin/orders/{orderId}/cancel",
                                                                "/api/admin/orders/{orderId}/shipping",
                                                                "/api/admin/orders/{orderId}/confirmed",
                                                                "/api/admin/orders/{orderId}/shipping",
                                                                "/api/admin/products/{productId}/update",
                                                                "/api/cart/add", "/api/item/{cartItemId}",
                                                                "/api/address/update")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.DELETE, "/api/item/{cartItemId}",
                                                                "/api/admin/orders/{orderId}/delete",
                                                                "/api/admin/products/{productId}/delete",
                                                                "api/item/{cartItemId}",
                                                                "/api/address/delete/{addressId}")
                                                .permitAll().anyRequest().authenticated());
                return http.build();
        }
}
