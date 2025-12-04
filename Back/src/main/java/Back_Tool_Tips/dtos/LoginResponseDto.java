package Back_Tool_Tips.dtos;

public class LoginResponseDto {
  private final String token;

  public LoginResponseDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
