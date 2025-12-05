package backtooltips;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import backtooltips.dtos.LoginUserDto;
import backtooltips.dtos.RegisterUserDto;
import backtooltips.models.User;
import backtooltips.services.AuthentificationService;
import backtooltips.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class AuthentificationControllerTest {

  // Injection de MockMvc, l'outil pour simuler les requêtes HTTP
  @Autowired private MockMvc mockMvc;

  // Utilisé pour sérialiser les objets Java en JSON (pour le corps de la requête)
  @Autowired private ObjectMapper objectMapper;

  // Simule le service d'authentification (on contrôle son comportement)
  @MockitoBean private AuthentificationService authenticationService;

  // Simule le service JWT (on contrôle son comportement)
  @MockitoBean private JwtService jwtService;

  private RegisterUserDto registerDto;
  private LoginUserDto loginDto;
  private User mockUser;
  private final String mockToken = "mock.jwt.token";

  @BeforeEach
  void setUp() {
    // Objets DTO de la requête
    registerDto = new RegisterUserDto("Test User", "test@example.com", "password");
    loginDto = new LoginUserDto("test@example.com", "password");

    // Objet User simulé retourné par le service
    mockUser = new User().setName("Test User").setEmail("test@example.com").setId(1);
  }

  // -------------------------------------------------------------------------
  // TEST DE L'ENDPOINT /register
  // -------------------------------------------------------------------------

  @Test
  void register_ShouldReturn200AndJwtToken() throws Exception {
    // Configuration (Arrange)

    // Simuler le comportement du service : quand signup est appelé, retourner mockUser
    when(authenticationService.signup(any(RegisterUserDto.class))).thenReturn(mockUser);

    // Simuler le comportement du service JWT : quand generateToken est appelé, retourner mockToken
    when(jwtService.generateToken(any(User.class))).thenReturn(mockToken);

    // Exécution et Vérification (Act & Assert)
    mockMvc
        .perform(
            post("/api/auth/register")
                // Définir le type de contenu de la requête (JSON)
                .contentType(MediaType.APPLICATION_JSON)
                // Définir le corps de la requête (DTO sérialisé en JSON)
                .content(objectMapper.writeValueAsString(registerDto)))

        // Vérifications
        .andExpect(status().isOk()) // Doit retourner un statut 200 OK
        // Vérifie la structure JSON de la réponse
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        // Vérifie le contenu JSON spécifique du token
        .andExpect(jsonPath("$.token").value(mockToken));
  }

  // -------------------------------------------------------------------------
  // TEST DE L'ENDPOINT /login
  // -------------------------------------------------------------------------

  @Test
  void authenticate_ShouldReturn200AndJwtToken_WhenCredentialsAreValid() throws Exception {
    // Configuration (Arrange)

    // Simuler le comportement du service : quand authenticate est appelé, retourner mockUser
    when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(mockUser);

    // Simuler le comportement du service JWT : quand generateToken est appelé, retourner mockToken
    when(jwtService.generateToken(any(User.class))).thenReturn(mockToken);

    // Exécution et Vérification (Act & Assert)
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))

        // Vérifications
        .andExpect(status().isOk()) // Doit retourner un statut 200 OK
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.token").value(mockToken));
  }

  @Test
  void authenticate_ShouldReturn401_WhenCredentialsAreInvalid() throws Exception {
    // Configuration (Arrange)

    // Simuler le cas où l'authentification échoue (BadCredentialsException)
    when(authenticationService.authenticate(any(LoginUserDto.class)))
        .thenThrow(
            new org.springframework.security.authentication.BadCredentialsException(
                "Invalid email or password"));

    // Exécution et Vérification (Act & Assert)
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))

        // Vérifications
        // La couche de sécurité de Spring renvoie généralement 401 Unauthorized
        .andExpect(status().isUnauthorized());
  }
}
