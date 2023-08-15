package labseq.com.backend_api.integrationTests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


/**
 * Integration testing where the full context of the Spring Boot Application is loaded, using the SpringBootTest mode
 * and the RestAssured library as the REST API entry point.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LabseqController_integrationTest {
    private final static String BASE_URI = "http://localhost:";

    @LocalServerPort
    private int randomServerPort;

    @Test
    void whenGetLabseqValue_withInvalidIndex_thenReturnStatusNotFound() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/10")
                .then().statusCode(200)
                .assertThat()
                .body(Matchers.equalTo("3"));
    }

    @Test
    void whenGetLabseqValue_withValidIndex_thenReturnStatusOK() throws Exception {
        RestAssured.when()
                .get(BASE_URI + randomServerPort + "/labseq/-10")
                .then().statusCode(404);
    }
}
