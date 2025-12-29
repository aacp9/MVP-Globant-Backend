package cl.aacp9.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

	@NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;
}
