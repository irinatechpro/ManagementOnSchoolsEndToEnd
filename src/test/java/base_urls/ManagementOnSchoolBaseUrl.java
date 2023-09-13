package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static utilities.ManagementOnSchoolAuthentication.generateToken;

public class ManagementOnSchoolBaseUrl {

    public static RequestSpecification spec;

    public static void setUp(){
        spec = new RequestSpecBuilder().
                addHeader("Authorization", generateToken()).
                setBaseUri("https://managementonschools.com/app").
                build();
    }
}
