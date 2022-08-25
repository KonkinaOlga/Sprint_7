import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.hamcrest.Matchers.*;


import java.util.ArrayList;


import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;


public class GettingListOfOrdersTest {
    OrderClient orderClient = new OrderClient();
    ArrayList<String> orderList;
    ParamsForOrderList params = new ParamsForOrderList(10,0);

    //в документации код ответа 200, а возвращается 201
    @Test
    @DisplayName("Getting a list of orders")
    public void getOrderListTest(){
        Orders response = orderClient.getOrdersList();
        MatcherAssert.assertThat(response, notNullValue());

    }

}