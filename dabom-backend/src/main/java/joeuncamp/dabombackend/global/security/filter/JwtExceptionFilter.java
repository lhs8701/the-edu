package joeuncamp.dabombackend.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import joeuncamp.dabombackend.global.error.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            setErrorResponse(request, response, e);
        }
    }
    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable e) throws IOException {
        final Map<String, Object> body = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();
        ErrorCode errorCode = ErrorCode.findByName(e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        body.put("name", errorCode.name());
        body.put("code", errorCode.getCode());
        body.put("message", errorCode.getMessage());
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
