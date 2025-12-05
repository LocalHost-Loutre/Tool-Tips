package backtooltips.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * DTO (Data Transfer Object) représentant les informations d'un utilisateur. Cette classe est
 * utilisée pour renvoyer les détails d'un utilisateur authentifié.
 */
public class UserGetMe {
  private Integer id;
  private String name;
  private String email;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
