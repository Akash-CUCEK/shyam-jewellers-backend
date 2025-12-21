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
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.contains("/refreshToken")
                || uri.contains("/login")
                || uri.contains("/verify")
                || uri.contains("/verifyOtp")) {

            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        log.info("Request URI: {}, Authorization Header: {}", uri, authHeader);

        try {
            if (authHeader != null
                    && authHeader.startsWith("Bearer ")
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                String jwt = authHeader.substring(7);

                if (jwtUtil.validateToken(jwt)) {

                    String username = JwtUtil.getUsername(jwt);
                    String role = JwtUtil.getRole(jwt);

                    UserDetails userDetails =
                            switch (role) {
                                case "ADMIN", "SUPER_ADMIN" ->
                                        adminUserDetailsService.loadUserByUsername(username);
                                case "USER" ->
                                        normalUserDetailsService.loadUserByUsername(username);
                                default ->
                                        throw new RuntimeException("Invalid role: " + role);
                            };

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("🚨 JWT Filter error", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Unauthorized\"}");
        }
    }
}
