package backtooltips.controllers;

import backtooltips.dtos.AuthenteResponseDto;
import backtooltips.dtos.LoginUserDto;
import backtooltips.dtos.RegisterUserDto;
import backtooltips.models.User;
import backtooltips.services.AuthentificationService;
import backtooltips.services.JwtService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Contrôleur pour les opérations d'authentification des utilisateurs. */
@RequestMapping("/api/auth")
@Slf4j
@Tag(name = "Auhtentification", description = "Endpoint for Authent / Endpoint concernant l'authentification")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal serveur Error",
                        content = @Content
                ),
        })
@RestController
public class AuthentificationController {
  private final JwtService jwtService;
  private final AuthentificationService authenticationService;

  public AuthentificationController(
      JwtService jwtService, AuthentificationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  /**
   * Endpoint pour l'inscription d'un nouvel utilisateur.
   *
   * @param registerUserDto Les informations d'inscription de l'utilisateur.
   * @return La réponse HTTP contenant les informations de l'utilisateur inscrit.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthenteResponseDto> register(
      @RequestBody RegisterUserDto registerUserDto) {
    User registeredUser = authenticationService.signup(registerUserDto);
    String jwtToken = jwtService.generateToken(registeredUser);

    AuthenteResponseDto response = new AuthenteResponseDto(jwtToken);

    return ResponseEntity.ok(response);
  }

  /**
   * Endpoint pour l'authentification d'un utilisateur existant.
   *
   * @param loginUserDto Les informations de connexion de l'utilisateur.
   * @return La réponse HTTP contenant le token JWT d'authentification.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthenteResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
    User authenticatedUser = authenticationService.authenticate(loginUserDto);
    String jwtToken = jwtService.generateToken(authenticatedUser);

    AuthenteResponseDto response = new AuthenteResponseDto(jwtToken);

    return ResponseEntity.ok(response);
  }
}
