package com.nvd.electroshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Nếu bạn dùng REST API
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**", "/public/**").permitAll() // ✅ Cho phép không cần login
//                        .anyRequest().authenticated() // Những đường khác thì cần login
                                .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // hoặc dùng formLogin() tùy bạn

        return http.build();
    }
}
