import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Properties;

public class BaseRest {

    public RequestSpecification REQUEST;


    public BaseRest() {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            //Rest Assured config
            RestAssured.baseURI = props.getProperty("api.uri");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //basic request setting
        REQUEST = RestAssured.given().contentType(ContentType.JSON);
    }
}
