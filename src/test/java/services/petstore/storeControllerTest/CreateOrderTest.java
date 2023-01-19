package services.petstore.storeControllerTest;

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
import services.petstore.controllers.store.entity.Order;
import utils.TestBase;

import java.time.LocalDate;

import static common.Constants.BASE_PETSTORE_URL;
import static services.petstore.controllers.store.endpoint.Endpoints.V2_STORE_POST_ORDER_ENDPOINT;

public class CreateOrderTest extends TestBase {

    private Faker faker;
    private LocalDate date;
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
    @DisplayName("Place correct order. Check status code 200")
    public void testCreateOrder200() {
        date = LocalDate.now();
        Order successOrder = Order.builder()
                .id(1)
                .petId(2)
                .quantity(3)
                .shipDate(date.toString())
                .status("placed")
                .complete(true)
                .build();
        RestAssured
                .given()
                .spec(requestSpecification)
                .and()
                .body(successOrder)
                .post(V2_STORE_POST_ORDER_ENDPOINT)
                .then()
                .log()
                .ifError()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}