package obss.project.basket.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import obss.project.basket.config.security.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logRequestDetails(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Response status: {}", response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            log.error("Exception occurred: ", ex);
        }
    }

    void logRequestDetails(HttpServletRequest request) {
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
