package backtooltips.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/** DTO (Data Transfer Object) représentant les informations de connexion d'un utilisateur. */
public class LoginUserDto {
  private String email;
  private String password;

  /**
   * Renvoie une représentation textuelle de l'objet LoginUserDto.
   *
   * @return Une chaîne représentant l'objet LoginUserDto.
   */
  @Override
  public String toString() {
    return "LoginUserDto{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
  }
}
