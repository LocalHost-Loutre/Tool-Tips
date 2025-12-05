package backtooltips.dtos;

import lombok.Getter;

@Getter
public class AuthenteResponseDto {
  private final String token;

  public AuthenteResponseDto(String token) {
    this.token = token;
  }
}
