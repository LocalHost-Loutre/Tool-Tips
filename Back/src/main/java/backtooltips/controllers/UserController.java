package backtooltips.controllers;

import backtooltips.dtos.UserGetMe;
import backtooltips.models.User;
import backtooltips.services.UserService;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Contrôleur pour la gestion des utilisateurs. */
@RequestMapping("/api")
@Slf4j
@Tag(name = "User / Utilisateur", description = "Endpoint for User / Endpoint concernant utilisateurs")
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
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Endpoint pour récupérer les informations de l'utilisateur actuellement authentifié.
   *
   * @param user L'utilisateur actuellement authentifié.
   * @return Les informations de l'utilisateur actuellement authentifié.
   */
  @GetMapping("/auth/me")
  public UserGetMe getCurrentUser(@AuthenticationPrincipal User user) {
    return userService.getCurrentUser(user);
  }

  /**
   * Endpoint pour récupérer la liste de tous les utilisateurs.
   *
   * @return La réponse HTTP contenant la liste de tous les utilisateurs.
   */
  @GetMapping("/")
  public ResponseEntity<List<UserGetMe>> allUsers() {
    List<UserGetMe> users = userService.allUsers();

    return ResponseEntity.ok(users);
  }

  /**
   * Endpoint pour récupérer les informations d'un utilisateur spécifique par son ID.
   *
   * @param id L'ID de l'utilisateur.
   * @return Les informations de l'utilisateur spécifié.
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<UserGetMe> getUserById(@PathVariable Integer id) {
    Optional<User> userOptional = userService.findById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      UserGetMe userGetMe = userService.getCurrentUser(user);
      return ResponseEntity.ok(userGetMe);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
