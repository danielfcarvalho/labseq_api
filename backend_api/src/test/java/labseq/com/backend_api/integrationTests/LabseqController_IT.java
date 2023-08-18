package labseq.com.backend_api.integrationTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;


/**
 * Integration testing where the full context of the Spring Boot Application is loaded, using the SpringBootTest mode
 * and the RestAssured library as the REST API entry point.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LabseqController_IT {
    private final static String BASE_URI = "http://localhost:";

    @LocalServerPort
    private int randomServerPort;

    @Test
    @Order(1)
    void whenGetLabseqValue_withInvalidIndex_thenReturnStatusNotFound() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/-10")
                .then().statusCode(404);
    }

    // Includes testing cache behavior
    @Test
    @Order(2)
    void whenGetLabseqValue_withValidIndex_thenReturnStatusOK() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/10")
                .then().statusCode(200)
                .assertThat()
                .body("Value", is("3")).and()
                .body("$", hasKey("Execution Time"));

        // Verify that the cache was hit only the necessary number of times to do this calculation
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/cache/all")
                .then().statusCode(200)
                .assertThat()
                .body("size()", is(8))
                .body("10", is(3));
    }

    // Includes testing cache behavior
    @Test
    @Order(3)
    void whenGetLabseqValue_withValidIndex_storedCache_thenReturnStatusOK() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/10")
                .then().statusCode(200)
                .assertThat()
                .body("Value", is("3s")).and()
                .body("$", hasKey("Execution Time"));

        // Verify that the cache was not hit any more times
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/cache/all")
                .then().statusCode(200)
                .assertThat()
                .body("size()", is(8))
                .body("10", is(3));
    }


}
