package backtooltips.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Configuration de la sécurité de l'application. */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthentificationFilter jwtAuthenticationFilter;

  public SecurityConfiguration(
      JwtAuthentificationFilter jwtAuthenticationFilter,
      AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  /**
   * Configuration de la chaîne de filtres de sécurité.
   *
   * @param http La configuration de sécurité HTTP.
   * @return La chaîne de filtres de sécurité configurée.
   * @throws Exception Si une erreur survient lors de la configuration de la sécurité.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // 1. Désactivation du CSRF (approche lambda moderne)
        .csrf(AbstractHttpConfigurer::disable)

        // 2. Configuration des requêtes autorisées
        .authorizeHttpRequests(
            authorizeRequests ->
                authorizeRequests
                    .requestMatchers(
                        "/api/auth/**",
                        "/images/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html")
                    .permitAll()
                    .anyRequest()
                    .authenticated())

        // 3. Correction : Utiliser un Customizer pour les en-têtes
        .headers(
            headers ->
                headers.contentTypeOptions(
                    Customizer.withDefaults()) // Correction de la signature de la méthode
            )

        // 4. Gestion de Session
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 5. Fournisseur d'Authentification et Filtre JWT
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
