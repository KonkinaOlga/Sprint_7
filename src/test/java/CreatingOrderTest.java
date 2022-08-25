import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreatingOrderTest {
    private final String[] colors;
    private Order order = OrderGenerator.getOrder();
    OrderClient orderClient = new OrderClient();
    private int orderTruck;

    public CreatingOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Creating an order with parameterization (color)")
    public void OrderCanBeCreatedTest() {
        order.setColor(colors);

        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("invalid code create order", SC_CREATED, statusCode);
        orderTruck =response.extract().path("track");
        Assert.assertTrue(orderTruck>0);
    }
}
