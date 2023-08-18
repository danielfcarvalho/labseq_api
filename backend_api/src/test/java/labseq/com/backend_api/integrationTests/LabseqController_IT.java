package labseq.com.backend_api.integrationTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    void whenGetLabseqValue_withInvalidIndex_thenReturnStatusNotFound() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/10")
                .then().statusCode(200)
                .assertThat()
                .body("Value", is("3")).and()
                .body("$", hasKey("Execution Time"));
    }

    @Test
    void whenGetLabseqValue_withValidIndex_thenReturnStatusOK() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/-10")
                .then().statusCode(404);
    }
}
