package backtooltips.configurations;

import backtooltips.repositorys.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Configuration de l'application. */
@Configuration
public class ApplicationConfiguration {
  private final UserRepository userRepository;

  public ApplicationConfiguration(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // --- Beans de Configuration ---

  /**
   * Définit un service pour récupérer les détails de l'utilisateur par son nom d'utilisateur
   * (email).
   *
   * @return Un service pour récupérer les détails de l'utilisateur.
   */
  @Bean
  UserDetailsService userDetailsService() {
    return username ->
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Définit un encodeur de mots de passe utilisant l'algorithme BCrypt.
   *
   * @return Un encodeur de mots de passe BCrypt.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Définit un fournisseur d'authentification utilisant un DAO pour récupérer les détails de
   * l'utilisateur. Les dépendances sont injectées par Spring.
   *
   * @param userDetailsService Le service de détails utilisateur injecté.
   * @param passwordEncoder L'encodeur de mot de passe injecté.
   * @return Un fournisseur d'authentification DAO.
   */
  @Bean
  AuthenticationProvider authenticationProvider(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    // Initialisation avec le constructeur qui prend UNIQUEMENT le UserDetailsService.
    // C'est ce que l'erreur "required:
    // org.springframework.security.core.userdetails.UserDetailsService" indique.
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);

    // Le PasswordEncoder est ensuite injecté via le setter.
    authProvider.setPasswordEncoder(passwordEncoder);

    return authProvider;
  }

  /**
   * Définit un gestionnaire d'authentification à partir de la configuration Spring Security.
   *
   * @param config La configuration d'authentification.
   * @return Un gestionnaire d'authentification.
   * @throws Exception Si une erreur survient lors de la récupération du gestionnaire
   *     d'authentification.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
