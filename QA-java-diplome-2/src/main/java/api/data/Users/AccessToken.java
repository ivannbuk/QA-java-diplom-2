package api.data.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {
    public static final String regexAccessToken = "Bearer " + ".*";

    private String accessToken;
}
