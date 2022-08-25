import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;


public class CourierClient extends RestClient {

    private static final String COURIER_CREATE_URL = "/api/v1/courier";
    private static final String COURIER_DELETE_URL = "/api/v1/courier/";
    private static final String COURIER_LOGIN_URL = "/api/v1/courier/login";

    @Step("Create new courier {courier}")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATE_URL)
                .then();
    }

    @Step("Delete courier {id}")
    public ValidatableResponse delete (int id) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", id)
                .when()
                .delete(COURIER_DELETE_URL+"{id}")
                .then();
    }

    @Step("Login {courier} courier")
    public ValidatableResponse login (CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_LOGIN_URL)
                .then();
    }

}
