package obss.project.basket.config.security;


import lombok.AllArgsConstructor;
import obss.project.basket.entity.Seller;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(HttpMethod.DELETE, "/v1/products/delete/**").hasAuthority("SELLER")
                            .requestMatchers(HttpMethod.GET,"/v1/sellers/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/v1/products/**").permitAll()
                            .requestMatchers("/v1/products/search").permitAll()
                            .requestMatchers("/v1/users/*/blacklist/**").hasAuthority("USER")
                            .requestMatchers("v1/users/blacklisted-sellers").hasAuthority("USER")
                            .requestMatchers("/upload/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/products/add").hasAuthority("SELLER")
                            .requestMatchers(HttpMethod.GET, "/v1/products").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/v1/users/**").hasAuthority("USER")
                            .requestMatchers("/v1/products/search/**").permitAll()
                            .requestMatchers("/v1/products/search").permitAll()
                            .requestMatchers("/v1/products/search/tags").permitAll()
                            .requestMatchers("/v1/tags").permitAll()
                            .requestMatchers("/v1/products/user/favorites/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/v1/**").permitAll()
                            .requestMatchers("/v1/auth/refresh-token").permitAll()
                            .requestMatchers("/v1/users/favorites").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST, "/v1/products/like/**").hasAuthority("USER")
                            .requestMatchers( HttpMethod.POST,"/v1/products/unlike/**").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST, "/v1/users/**").hasAuthority("USER")
                            .requestMatchers(HttpMethod.GET, "/v1/tags").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/products/add").hasAuthority("SELLER")
                            .requestMatchers(HttpMethod.PUT, "/v1/products/update/**").hasAuthority("SELLER")
                            .requestMatchers(HttpMethod.DELETE, "/v1/products/delete/**").hasAuthority("SELLER")
                            .requestMatchers(HttpMethod.GET, "/api/admin/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/v1/products/upload/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/upload/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/v1/users/**").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST, "/v1/products/upload").hasAuthority("SELLER")
                            .requestMatchers(HttpMethod.GET, "/v1/products").permitAll()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // allow Swagger UI
                            .requestMatchers("/uploads/**").permitAll()
                            .requestMatchers("/upload/**").permitAll()
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
