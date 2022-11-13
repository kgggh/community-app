package com.artinus.gateway.filter;

import com.artinus.gateway.exception.UnauthorizedException;
import com.artinus.gateway.helper.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private JwtTokenProvider jwtProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtProvider) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
            if(CollectionUtils.isEmpty(authorization)) {
                throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
            String token = authorization.get(0);
            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            if(!jwtProvider.validateToken(token)) {
                throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            return chain.filter(exchange);
        });
    }

    public static class Config { }
}