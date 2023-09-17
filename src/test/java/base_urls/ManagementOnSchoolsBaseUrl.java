package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ManagementOnSchoolsBaseUrl {

    public static RequestSpecification spec;//null

    public static void setSpec(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://managementonschools.com/app")
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbkpvaG4iLCJpYXQiOjE2OTQ5NjA0ODYsImV4cCI6MTY5NDk2OTEyNn0.oNvsJEYsQhkv1FxlmwS3a2ZpSwfp9fh0PoT05hEGNOHPLnQEzRORoOxoBM5L3wFXW_B-ZCe7-KIk_o7Q2l7p2w")
                .build();
    }


}
