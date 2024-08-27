package obss.project.basket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NotNull
@NoArgsConstructor
public class SellerDto {

    private Long id;
    private String name;
    private String surname;
    private String email;

}
