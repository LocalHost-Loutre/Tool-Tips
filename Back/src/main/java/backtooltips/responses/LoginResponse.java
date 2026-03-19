package backtooltips.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** Réponse renvoyée après une demande d'authentification. */
public class LoginResponse {
  private String token;
  private long expiresIn;

  /**
   * Renvoie une représentation textuelle de l'objet LoginResponse.
   *
   * @return Une chaîne représentant l'objet LoginResponse.
   */
  @Override
  public String toString() {
    return "LoginResponse{" + "token='" + token + '\'' + ", expiresIn=" + expiresIn + '}';
  }
}
