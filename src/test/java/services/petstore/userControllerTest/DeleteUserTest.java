package services.petstore.userControllerTest;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.petstore.controllers.users.entity.User;
import utils.TestBase;

import static common.Constants.BASE_PETSTORE_URL;
import static services.petstore.controllers.users.endpoint.Endpoints.V2_USERS_DELETE_ENDPOINT;

public class DeleteUserTest extends TestBase {

    private Faker faker;
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(BASE_PETSTORE_URL)
            .log(LogDetail.ALL)
            .build();

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    @DisplayName("Delete non-existent. User not found. Status code 404")
    public void testDeleteUser404() {
        User nonExistentUser = User.builder()
                .username("nonExistentUserName")
                .build();
        RestAssured
                .given()
                .spec(requestSpecification)
                .and()
                .log()
                .all()
                .delete(V2_USERS_DELETE_ENDPOINT + nonExistentUser.getUsername())
                .then()
                .log()
                .ifError()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
