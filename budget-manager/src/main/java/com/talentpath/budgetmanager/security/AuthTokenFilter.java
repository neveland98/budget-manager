package com.talentpath.budgetmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Value("${budget.app.jwtsecret}")
    private String secret;

    @Autowired
    UserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractJwt(request);

        if(token != null) {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            String userName = claims.getBody().getSubject();

            UserDetails details = detailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken convertedToken =
                    new UsernamePasswordAuthenticationToken(
                            details,
                            null,
                            details.getAuthorities()
                    );
            convertedToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(convertedToken);
        }
        else {
            //here we didn't send the credentials, but this is ok if it is a sign in
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request,response);
    }


    private String extractJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
