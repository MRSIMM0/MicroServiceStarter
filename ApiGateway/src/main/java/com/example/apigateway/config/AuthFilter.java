package com.example.apigateway.config;

import com.example.apigateway.DTO.UserModelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;
    @Autowired
    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;

    }

    @Override
    public org.springframework.cloud.gateway.filter.GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missing auth header");
            }

            String authorization = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] parts = authorization.split(" ");

            if(parts.length != 2 || !"Bearer".equals(parts[0])){
                throw new RuntimeException("Incorrect auth");
            }

                return webClientBuilder.build()
                        .post()
                        .uri("http://auth-service/api/v1/validate")
                        .header(HttpHeaders.AUTHORIZATION, parts[1])
                        .retrieve()
                        .bodyToMono(UserModelDTO.class)
                        .map(userDto -> {
                            exchange.getRequest()
                                    .mutate()
                                    .header("x-auth-user-id", String.valueOf(userDto.getId()));
                            return exchange;
                        }).flatMap(chain::filter);

        });
    }

    public static class Config {
    }
}
