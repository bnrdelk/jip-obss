package obss.project.basket.dto;

import lombok.Data;
import obss.project.basket.entity.Role;

import java.util.Set;

@Data
public class AuthResponseDto {

    private String email;
    private String accessToken;
    private Long userId;
    private Set<Role> roles;

}
