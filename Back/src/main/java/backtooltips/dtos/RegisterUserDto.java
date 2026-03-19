package backtooltips.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * DTO (Data Transfer Object) représentant les informations d'inscription d'un nouvel utilisateur.
 */
public class RegisterUserDto {
  private String email;
  private String password;
  private String name;

  /**
   * Renvoie une représentation textuelle de l'objet RegisterUserDto.
   *
   * @return Une chaîne représentant l'objet RegisterUserDto.
   */
  @Override
  public String toString() {
    return "RegisterUserDto{"
        + "email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", name='"
        + name
        + '\''
        + '}';
  }
}
