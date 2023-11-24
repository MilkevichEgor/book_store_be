package com.example.bookstorebe.security;

import com.example.bookstorebe.security.jwt.AuthEntryPointJwt;
import com.example.bookstorebe.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for the web security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  UserDetailsServiceImpl userDetailsService;
  AuthEntryPointJwt unauthorizedHandler;
  JwtUtils jwtUtils;

  @Autowired
  WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                    AuthEntryPointJwt unauthorizedHandler,
                    JwtUtils jwtUtils) {
    this.userDetailsService = userDetailsService;
    this.unauthorizedHandler = unauthorizedHandler;
    this.jwtUtils = jwtUtils;
  }

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter(jwtUtils, userDetailsService);
  }

  /**
   * Creates and configures the DaoAuthenticationProvider for authentication.
   *
   * @return The configured DaoAuthenticationProvider instance.
   */
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain for the application.
   * Disables CSRF protection, sets the authentication entry point for exceptions,
   * configures session management, and authorizes requests.
   * Adds an authentication provider and a JWT token filter to the filter chain.
   *
   * @param http the HttpSecurity object to configure
   * @return the configured SecurityFilterChain object
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers("/test/**").permitAll()
                            .requestMatchers("/user/me").permitAll()
                            .anyRequest().authenticated());

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(),
            UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
