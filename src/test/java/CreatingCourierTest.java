import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


public class CreatingCourierTest {

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
    @DisplayName("Courier creating")
    public void courierCanBeCreatedTest(){

        ValidatableResponse createdResponse = courierClient.create(courier);

        int statusCode = createdResponse.extract().statusCode();
        Assert.assertEquals("invalid response code create", SC_CREATED, statusCode);

        boolean isCreated = createdResponse.extract().path("ok");
        Assert.assertTrue("Courier is not created", isCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("invalid response code login", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");

    }
    @Test
    @DisplayName("Creating two identical couriers")
    public void identicalCourierCanNotBeCreatedTest(){
        ValidatableResponse createdResponseFirst = courierClient.create(courier);
        int statusCodeFirst = createdResponseFirst.extract().statusCode();
        Assert.assertEquals("invalid response code create", SC_CREATED, statusCodeFirst);
        boolean isCreated = createdResponseFirst.extract().path("ok");
        Assert.assertTrue("Courier is not created", isCreated);

        ValidatableResponse createdResponseSecond = courierClient.create(courier);
        int statusCodeSecond = createdResponseSecond.extract().statusCode();
        Assert.assertEquals("invalid response code create", SC_CONFLICT, statusCodeSecond);
        String message = createdResponseSecond.extract().path("message");
        Assert.assertEquals("Message is not expected", "Этот логин уже используется. Попробуйте другой.", message);

    }

    @Test
    @DisplayName("Creating a courier without a password")
    public void courierWithoutPasswordCanNotBeCreatedTest(){
        ValidatableResponse createdResponse = courierClient.create(courierWithoutPassword);

        int statusCode = createdResponse.extract().statusCode();
        Assert.assertEquals("invalid response code create", SC_BAD_REQUEST, statusCode);
        String message = createdResponse.extract().path("message");
        Assert.assertEquals("Courier can not be created", "Недостаточно данных для создания учетной записи", message);

    }
}
