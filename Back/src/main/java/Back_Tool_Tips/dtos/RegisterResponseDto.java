package Back_Tool_Tips.dtos;

public class RegisterResponseDto {
  private final String token;

  public RegisterResponseDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
