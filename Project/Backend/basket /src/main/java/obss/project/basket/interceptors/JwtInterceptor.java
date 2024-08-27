package obss.project.basket.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import obss.project.basket.config.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;


// loglama methodu duplicate oldu, ortak çağrım daha iyi olur
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logRequestDetails(request);

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean isValid = jwtTokenProvider.validateToken(token);

            if (!isValid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    private void logRequestDetails(HttpServletRequest request) {
        StringBuilder requestInfo = new StringBuilder();

        requestInfo.append("Method: ").append(request.getMethod()).append("\n");
        requestInfo.append("Request URI: ").append(request.getRequestURI()).append("\n");
        requestInfo.append("Headers: ").append("\n");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            requestInfo.append("\t").append(headerName).append(": ").append(headerValue).append("\n");
        }

        log.info("Request Details:\n{}", requestInfo);
    }


}
