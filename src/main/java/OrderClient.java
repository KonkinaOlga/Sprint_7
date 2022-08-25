import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDER_CREATE_URL = "/api/v1/orders";
    private static final String ORDER_GET_LIST_URL = "/api/v1/orders?limit=10&page=0";

    @Step("Create new order {order}")
    public ValidatableResponse create (Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_CREATE_URL)
                .then();
    }

    @Step("Get order list")
    public Orders getOrdersList (){
        return given()
                .baseUri("http://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .get(ORDER_GET_LIST_URL)
                .body().as(Orders.class);
    }


}
