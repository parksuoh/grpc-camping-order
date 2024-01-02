package com.example.order.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.example.order.domains.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final AccessTokenAuthenticationFilter authenticationFilter;

    public WebSecurityConfig(
            AccessTokenAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(HttpMethod.GET,
                                        "/api/order/admin"
                                ).hasAuthority(Role.ROLE_ADMIN.toString())

                                .requestMatchers(HttpMethod.PATCH,
                                        "/api/order/admin"
                                ).hasAuthority(Role.ROLE_ADMIN.toString())

                                .requestMatchers(HttpMethod.GET,
                                        "/api/order/common"
                                ).hasAnyAuthority(Role.ROLE_USER.toString(), Role.ROLE_ADMIN.toString())


                                .requestMatchers(HttpMethod.POST,
                                        "/api/order/common"
                                ).hasAnyAuthority(Role.ROLE_USER.toString(), Role.ROLE_ADMIN.toString())



                                .requestMatchers(HttpMethod.GET,
                                        "/api/order/test"
                                ).permitAll()


                                .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}