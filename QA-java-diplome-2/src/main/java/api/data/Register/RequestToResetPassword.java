package api.data.Register;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestToResetPassword {

    @JsonProperty("email")
    private String email;
}