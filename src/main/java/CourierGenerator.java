import java.util.UUID;

public class CourierGenerator {

    static String login = UUID.randomUUID().toString().substring(0,10);
    static String password = UUID.randomUUID().toString().substring(0,10);
    static String firstName = UUID.randomUUID().toString().substring(0,10);

    public static Courier getDefault(){
        return new Courier(login, password, firstName);
    }

    public static Courier getCourierWithLoginOnly(){
        return new Courier(login,"", "");
    }
}
