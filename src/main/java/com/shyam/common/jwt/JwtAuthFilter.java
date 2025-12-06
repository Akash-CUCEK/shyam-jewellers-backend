package com.shyam.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AdminUserDetailsService adminUserDetailsService;
    private final NormalUserDetailsService normalUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        log.info("➡️ Request URI: {}, Authorization Header: {}", request.getRequestURI(), authHeader);

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                log.info("Extracted JWT: {}",jwt);

                if (jwtUtil.validateToken(jwt)) {
                    log.info("✅ JWT is valid");

                    username = JwtUtil.getUsername(jwt);
                    String role = JwtUtil.getRole(jwt);

                    log.info("Username from JWT: {}, Role: {}", username, role);

                    UserDetails userDetails;
                    switch (role.toUpperCase()) {
                        case "ADMIN", "SUPER_ADMIN" -> {
                            userDetails = adminUserDetailsService.loadUserByUsername(username);
                            log.info("🔐 Loaded ADMIN user details for {}", username);
                        }
                        case "USER" -> {
                            userDetails = normalUserDetailsService.loadUserByUsername(username);
                            log.info("🔐 Loaded NORMAL user details for {}", username);
                        }
                        default -> {
                            log.error("Invalid role in JWT: {}", role);
                            throw new RuntimeException("Invalid role in JWT: " + role);
                        }
                    }

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("🔓 Authentication successful for {}", username);

                } else {
                    log.warn("⚠️ JWT is invalid or expired");
                }
            } else {
                log.warn("⚠️ No Authorization header or invalid format");
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("🚨 JWT Filter exception", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing token\"}");
        }
    }
}
