import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasKey;

public class LoginCourierTest {


    private Courier courier;
    private Courier courierWithoutPassword;
    private CourierClient courierClient;
    private int courierId;


    @Before
    public void setUp(){
        courier = CourierGenerator.getDefault();
        courierWithoutPassword = CourierGenerator.getCourierWithLoginOnly();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("courier login positive")
    public void courierCanBeLoginTest(){
        ValidatableResponse createdResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("invalid createdResponse code login", SC_OK, loginStatusCode);

        loginResponse.body("$", hasKey("id"));
        courierId = loginResponse.extract().path("id");
        Assert.assertTrue("Id is null" , courierId > 0);
    }

    @Test
    @DisplayName("courier login negative")
    public void courierCanNotBeLoginWithWrongPasswordTest(){
        ValidatableResponse createdResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.CourierCredentialsWithWrongPassword(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("invalid loginResponse code", SC_NOT_FOUND, loginStatusCode);
        String message = loginResponse.extract().path("message");
        Assert.assertEquals("Message is not expected", "Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("courier login only login")
    public void courierCanNotBeLoginWithoutPassword(){
        ValidatableResponse createdResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierWithoutPassword));

        int loginStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("invalid response code login", SC_BAD_REQUEST, loginStatusCode);
        String message = loginResponse.extract().path("message");
        Assert.assertEquals("Message is not expected", "Недостаточно данных для входа", message);
    }

}
