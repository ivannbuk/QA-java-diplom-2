package AuthorizationUser;

import api.data.Login.LoginCredentials;
import api.data.Login.LoginSuccess;
import api.data.Register.RegisterCredentials;
import api.data.Users.AccessToken;
import api.data.Users.UsersFactory;
import api.services.BaseUserApiMethod;
import api.services.UserApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static api.data.Users.AccessToken.regexAccessToken;
import static org.hamcrest.Matchers.*;


public class TestSuiteLoginUserSuccessfully {

    private RegisterCredentials registerCredentials;
    private UserApiService userApiService;
    private LoginCredentials loginCredentials;
    private LoginSuccess loginSuccess;
    private AccessToken accessToken;
    private BaseUserApiMethod baseUserApiMethod;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        baseUserApiMethod = new BaseUserApiMethod();
        accessToken = new AccessToken();
        loginCredentials = new LoginCredentials();
        registerCredentials = UsersFactory.getRandomUser();
        // register new user
        baseUserApiMethod.registerUserWithCurrent(registerCredentials);
    }

    @After
    public void tearDown() {
        // delete User
        accessToken.setAccessToken(loginSuccess.getAccessToken());
        baseUserApiMethod.deleteUserWithCurrent(accessToken);
    }

    @Feature("login user")
    @Test
    @DisplayName("Can login for valid user")
    public void testCanLoginForValidUser() {
        // given
        loginCredentials.setEmail(registerCredentials.getEmail());
        loginCredentials.setPassword(registerCredentials.getPassword());
        // expected
        loginSuccess = userApiService
                .loginUser(loginCredentials)
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("accessToken", matchesPattern(regexAccessToken)))
                .shouldHave(bodyField("refreshToken", notNullValue()))
                .shouldHave(bodyField("user.email", containsString(registerCredentials.getEmail())))
                .shouldHave(bodyField("user.name", containsString(registerCredentials.getName())))
                .asPojo(LoginSuccess.class);
    }
}
