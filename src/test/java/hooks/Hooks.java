package hooks;

import io.cucumber.java.Before;

import static base_urls.ManagementOnSchoolsBaseUrl.setSpec;

public class Hooks {
    //This class must be decalred in Runner class

    @Before("@Api_Test")//import io.cucumber.java.Before;
    public void beforeApi(){

        setSpec();

    }

}