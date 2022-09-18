package pl.simmo.authservice.DTO;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Data
@Builder
public class RegisterDTO {

    private Long id;

    private String username;

    private String password;

    private String email;
}
