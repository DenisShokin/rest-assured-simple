package petstore.userControllerTest;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.petstore.controllers.users.entity.User;
import utils.TestBase;

import static services.petstore.controllers.users.endpoint.Endpoints.USERS_GET_ENDPOINT;

public class CreateUserTest extends TestBase {

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    @DisplayName("Create correct user. Check status code 200")
    public void testCreateUser200() {
        User successUser = User.builder()
                .userStatus(1)
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .password(faker.internet().password())
                .username(faker.name().username())
                .phone(faker.phoneNumber().phoneNumber())
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .and()
                .body(successUser)
                .post(USERS_GET_ENDPOINT)
                .then()
                .log()
                .ifError()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

}
