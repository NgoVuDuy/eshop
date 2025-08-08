package com.nvd.electroshop.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvd.electroshop.dto.response.Message;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    private final Bucket bucket;

    public RateLimiterFilter() {

        Refill refill = Refill.intervally(20, Duration.ofMinutes(1));

        Bandwidth bandwidth = Bandwidth.classic(20, refill);

         bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("aplication/json");

            ObjectMapper objectMapper = new ObjectMapper();

            String message = objectMapper.writeValueAsString(new Message(0, "Quá nhiều yêu cầu. Vui lòng thử lại sau !"));
            response.getWriter().write(message);
        }
    }
}
