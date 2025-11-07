package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.List;  
import org.springframework.http.HttpMethod;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class WebConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedOrigins(List.of("http://localhost:4200", 
                                        "http://40.233.27.238"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    
@Bean
public WebFilter corsPreflightHandler() {
    return (exchange, chain) -> {
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            var response = exchange.getResponse();
            response.getHeaders().add("Access-Control-Allow-Origin", exchange.getRequest().getHeaders().getOrigin());
            response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
            response.getHeaders().add("Access-Control-Allow-Headers", "Authorization, Content-Type, Origin, Accept");
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.setStatusCode(org.springframework.http.HttpStatus.OK);
            return Mono.empty(); 
        }
        return chain.filter(exchange);
    };
}

}
