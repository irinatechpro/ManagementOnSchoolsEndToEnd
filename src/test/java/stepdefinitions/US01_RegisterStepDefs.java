package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pages.HomePage;
import pages.RegisterPage;
import utilities.Driver;
import utilities.JDBCUtils;
import utilities.ReusableMethods;

import java.sql.*;

import static base_urls.ManagementOnSchoolBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.JDBCUtils.executeQuery;
import static utilities.JSUtils.clickElementByJS;

public class US01_RegisterStepDefs {
    RegisterPage registerPage = new RegisterPage();
    HomePage homePage = new HomePage();
    private static String fakeUsername;
    private static String fakeSsn;
    private static String fakePhoneNumber;
    ResultSet resultSet;


    @Given("go to the {string}")
    public void goToThe(String url) {

        Driver.getDriver().get(url);

    }


    @Given("click on register")
    public void click_on_register() {

        homePage.registerLink.click();

    }


    @When("Enter name {string}, surname {string},birth_place {string}, phone_number {string},gender {string}, birth_day {string},ssn {string},username {string},password{string}")
    public void enterNameSurnameBirth_placePhone_numberGenderBirth_daySsnUsernamePassword(String name, String surname, String birth_place, String phone_number, String gender, String birth_day, String ssn, String username, String password) {
        Faker faker = new Faker();
        registerPage.nameInput.sendKeys(name);
        registerPage.surNameInput.sendKeys(surname);
        registerPage.birthPlaceInput.sendKeys(birth_place);
        fakePhoneNumber = faker.number().numberBetween(100, 999) + " " + faker.number().numberBetween(100, 999) + " " + faker.number().numberBetween(1000, 9999);
        registerPage.phoneNumberInput.sendKeys(fakePhoneNumber);

        if (gender.equalsIgnoreCase("male")) {
            clickElementByJS(registerPage.maleRadioButton);
        } else {
            clickElementByJS(registerPage.femaleRadioButton);
        }


        registerPage.birthDay.sendKeys(birth_day);
        fakeSsn = faker.idNumber().ssnValid();
        registerPage.ssnInput.sendKeys(fakeSsn);
        fakeUsername = faker.name().username();
        registerPage.userNameInput.sendKeys(fakeUsername);
        registerPage.passwordInput.sendKeys(password);
    }

    @Then("click on register button")
    public void click_on_register_button() throws InterruptedException {

        Thread.sleep(3000);
        RegisterPage registerPage = new RegisterPage();
        clickElementByJS(registerPage.registerButton);
        ReusableMethods.waitForVisibility(registerPage.alertMessage, 5);
        assertTrue(registerPage.alertMessage.getText().contains("Guest User registered"));

    }


    @Then("close browser")
    public void closeBrowser() {

        Driver.getDriver().quit();

    }

    @Given("connect to database")
    public void connect_to_database() throws SQLException {

        //connection = DriverManager.getConnection("jdbc:postgresql://managementonschools.com:5432/school_management", "select_user", "43w5ijfso");


    }

    @When("get guest user via username {string}")
    public void get_guest_user_via_username(String username) throws SQLException {

        // statement = connection.createStatement();

        String query = "select * from guest_user where username = '" + fakeUsername + "'";

        // resultSet = statement.executeQuery(query);
        resultSet = executeQuery(query);

    }

    @Then("validate body contains birthday {string}, birthplace {string}, gender {string}, name {string}, phoneNumber {string}, ssn {string}, surname {string}, username {string}")
    public void validate_body_contains_birthday_birthplace_gender_name_phone_number_ssn_surname_username(String birthday, String birthplace, String gender, String name, String phoneNumber, String ssn, String surname, String username) throws SQLException {

        resultSet.next();
        String actualBirthDay = resultSet.getString("birth_day");
        String actualBirthPlace = resultSet.getString("birth_place");
        String actualGender = resultSet.getString("gender");
        String actualName = resultSet.getString("name");
        String actualPhoneNumber = resultSet.getString("phone_number");
        String actualSsn = resultSet.getString("ssn");
        String actualSurname = resultSet.getString("surname");
        String actualUsername = resultSet.getString("username");


        assertEquals(birthday, actualBirthDay);
        assertEquals(birthplace, actualBirthPlace);
        assertEquals(gender, actualGender);
        assertEquals(name, actualName);
        assertEquals(fakePhoneNumber, actualPhoneNumber);
        assertEquals(fakeSsn, actualSsn);
        assertEquals(surname, actualSurname);
        assertEquals(fakeUsername, actualUsername);


    }

    Response response;

    @Given("Send get request by id {string}")
    public void sendGetRequestById(String id) {
        //https://managementonschools.com/app/guestUser/getAll?size=1000
        spec.pathParams("first", "guestUser", "second", "getAll").
                queryParams("size", "1000");

        response = given(spec).get("{first}/{second}");
        //response.prettyPrint();
    }

    @Then("body should be: username={string} ssn={string} name={string} surname={string} birthDay={string} birthPlace={string} phoneNumber={string} gender={string}")
    public void bodyShouldBeUsernameSsnNameSurnameBirthDayBirthPlacePhoneNumberGender(String username, String ssn, String name, String surname, String birthDay, String birthPlace, String phoneNumber, String gender) {

        JsonPath jsonPath = response.jsonPath();
        String actUsername = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.username").get(0).toString();
        String actSsn = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.ssn").get(0).toString();
        String actName = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.name").get(0).toString();
        String actSurname = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.surname").get(0).toString();
        String actBirthDay = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.birthDay").get(0).toString();
        String actBirthPlace = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.birthPlace").get(0).toString();
        String actPhoneNumber = jsonPath.getList("content.findAll{it.username=='" + fakeUsername + "'}.phoneNumber").get(0).toString();
        String actGender = jsonPath.getList("content.findAll{it.username=='"+fakeUsername+"'}.gender").get(0).toString();

        assertEquals(200, response.statusCode());
        assertEquals(fakeUsername, actUsername);
        assertEquals(fakeSsn, actSsn);
        assertEquals(name, actName);
        assertEquals(surname, actSurname);
        assertEquals(birthDay, actBirthDay);
        assertEquals(birthPlace, actBirthPlace);
        assertEquals(fakePhoneNumber, actPhoneNumber);
        assertEquals(gender, actGender);

        JDBCUtils.closeConnection();

    }


}