package obss.project.basket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInputDto implements Serializable {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 12)
    private String password;

    @Size(min = 2, max = 24)
    private String name;

    @Size(min = 2, max = 24)
    private String surname;
}
