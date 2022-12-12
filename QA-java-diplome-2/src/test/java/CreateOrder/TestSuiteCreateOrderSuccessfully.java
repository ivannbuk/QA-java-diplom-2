package CreateOrder;

import api.data.Ingredients.BurgerFactory;
import api.data.Ingredients.Ingredients;
import api.data.Login.LoginCredentials;
import api.data.Login.LoginSuccess;
import api.data.Register.RegisterCredentials;
import api.data.Register.RegisteredUser;
import api.data.Users.AccessToken;
import api.data.Users.UsersFactory;
import api.services.BaseUserApiMethod;
import api.services.OrdersApiService;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static api.conditions.Conditions.*;
import static org.hamcrest.Matchers.*;

public class TestSuiteCreateOrderSuccessfully {
    private OrdersApiService ordersApiService;
    private RegisterCredentials registerCredentials;
    private RegisteredUser registeredUser;
    private LoginSuccess loginSuccess;
    private LoginCredentials loginCredentials;
    private BaseUserApiMethod baseUserApiMethod;
    private AccessToken accessToken;

    @Before
    public void setUp() {
        ordersApiService = new OrdersApiService();
        registeredUser = new RegisteredUser();
        baseUserApiMethod = new BaseUserApiMethod();
        accessToken = new AccessToken();
        loginCredentials = new LoginCredentials();
        loginSuccess = new LoginSuccess();
    }

    @Feature("create order")
    @Test
    @DisplayName("Can create order without user authorization")
    public void testCanCreateOrderWithoutUserAuthorization() {
        // given
        Ingredients ingredients = ordersApiService.getIngredients().asPojo(Ingredients.class);

        //expected
        ordersApiService
                .createOrder(BurgerFactory.getRandomBurgerWithAll(ingredients))
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("name", containsString(MESSAGE_BURGER)))
                .shouldHave(bodyField("order.number", notNullValue()));
    }

    @Feature("create order")
    @Test
    @DisplayName("Can create order with user authorization")
    public void testCanCreateOrderWithUserAuthorization() {
        // given
        registerCredentials = UsersFactory.getRandomUser();
        // register and login new user
        registeredUser = baseUserApiMethod.registerUserWithCurrent(registerCredentials);
        baseUserApiMethod.setCurrentLoginCredentials(registerCredentials, loginCredentials);
        loginSuccess = baseUserApiMethod.loginUserWithCurrent(loginCredentials,registerCredentials);
        // get ingredients
        Ingredients ingredients = ordersApiService.getIngredients().asPojo(Ingredients.class);

        // expected
        ordersApiService
                .createOrder(BurgerFactory.getRandomBurgerWithAll(ingredients))
                .shouldHave(statusCode(STATUS_CODE_200))
                .shouldHave(bodyField("success", is(true)))
                .shouldHave(bodyField("name", containsString(MESSAGE_BURGER)))
                .shouldHave(bodyField("order.number", notNullValue()));

        // delete user
        accessToken.setAccessToken(loginSuccess.getAccessToken());
        baseUserApiMethod.deleteUserWithCurrent(accessToken);
    }
}
