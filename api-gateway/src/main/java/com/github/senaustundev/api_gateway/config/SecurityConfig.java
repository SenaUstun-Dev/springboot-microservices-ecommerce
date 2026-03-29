package com.github.senaustundev.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Profile("!test")
public class SecurityConfig {

        private final String[] freeResourceUrls = {
                        // "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                        // "/swagger-resources/**", "/api-docs/**", "/aggregate/**",
                        // "/actuator/prometheus"
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
                        throws Exception {
                return httpSecurity
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers(freeResourceUrls).permitAll()
                                                .anyRequest().authenticated())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                                                jwtAuthenticationConverter())))
                                // Gateway is stateless — no session needed
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                // Disable CSRF for stateless REST APIs
                                .csrf(AbstractHttpConfigurer::disable)
                                .build();
        }

        // This is the key modernization: extract roles from Keycloak's token structure
        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();

                // Keycloak puts roles at realm_access.roles, not in "scope"
                converter.setAuthoritiesClaimName("realm_access.roles");
                converter.setAuthorityPrefix("ROLE_");

                JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
                jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
                return jwtConverter;
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.applyPermitDefaultValues();
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE",
                                "OPTIONS", "HEAD"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
