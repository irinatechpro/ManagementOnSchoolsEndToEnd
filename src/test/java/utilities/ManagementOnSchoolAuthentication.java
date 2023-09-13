package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ManagementOnSchoolAuthentication {

    public static String generateToken() {
        String postBody = "{ \"password\": \"John.123\",  \"username\": \"AdminJohn\"\n" + "}";

        Response response = given().contentType(ContentType.JSON).body(postBody).post("https://managementonschools.com/app/auth/login");
        //response.prettyPrint();

        return response.jsonPath().getString("token");

    }

}
