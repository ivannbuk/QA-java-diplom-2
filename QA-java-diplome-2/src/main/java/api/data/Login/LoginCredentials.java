package api.data.Login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCredentials {

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;
}
