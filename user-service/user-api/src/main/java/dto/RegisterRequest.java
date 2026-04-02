package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email
    private String email;

    @NotBlank
    private String username;

    @Size(min = 8)
    @NotBlank
    private String password;
}
