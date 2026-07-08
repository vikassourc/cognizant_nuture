package com.cognizant.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Week 4 – API Gateway
 * Task: Microservices with API Gateway
 *
 * Global logging filter that runs on every request passing through the gateway.
 * Demonstrates:
 *   - Spring Cloud Gateway GlobalFilter
 *   - Reactive programming (Mono / WebFlux)
 *   - Pre and Post filter execution
 *
 * Logs:
 *   PRE  – method, path, headers before routing to downstream service
 *   POST – response status after downstream service responds
 */
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // ── PRE-FILTER: Log incoming request ─────────────────────────────
        log.info("═══════════════════════════════════════════════════════");
        log.info("  [API Gateway] Incoming Request");
        log.info("  Time   : {}", LocalDateTime.now());
        log.info("  Method : {}", request.getMethod());
        log.info("  Path   : {}", request.getURI().getPath());
        log.info("  Headers: {}", request.getHeaders().toSingleValueMap());

        long startTime = System.currentTimeMillis();

        // ── POST-FILTER: Log response after downstream responds ───────────
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long elapsed = System.currentTimeMillis() - startTime;
            log.info("  [API Gateway] Response");
            log.info("  Status  : {}", exchange.getResponse().getStatusCode());
            log.info("  Duration: {} ms", elapsed);
            log.info("═══════════════════════════════════════════════════════");
        }));
    }

    /**
     * Order = -1 ensures this filter runs before most other filters.
     * Lower number = higher priority.
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
