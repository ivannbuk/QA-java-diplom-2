package api.data.Users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutToken {

    @JsonProperty("token")
    private String token;
}
