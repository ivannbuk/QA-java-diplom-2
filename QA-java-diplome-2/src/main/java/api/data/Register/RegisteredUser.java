package api.data.Register;

import api.data.Login.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisteredUser {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("user")
    private User user;

    @JsonProperty("refreshToken")
    private String refreshToken;
}
