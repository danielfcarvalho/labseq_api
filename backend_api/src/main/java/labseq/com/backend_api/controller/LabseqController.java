package labseq.com.backend_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Labseq Controller", description = "Endpoint to fetch the desired value of the Labseq sequence")
@RestController
@RequestMapping("labseq")
public class LabseqController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LabseqController.class);
    LabseqService labseqService;

    public LabseqController(LabseqService labseqService) {
        this.labseqService = labseqService;
    }

    /**
     * GET endpoint that fetches the labseq sequence value at a specified index
     * @param n - The index of the sequence to be fetched. Must be bigger or equal to 0.
     * @return a HTTP STATUS OK with the corresponding value
     * @throws IncorrectParameterValueException - In case N < 0, an exception is thrown and a HTTP STATUS NOT FOUND is returned
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid N returns Labseq sequence value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"Execution Time\": \"0.0s\", \"Value\": \"182376579\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid N supplied (Should be N >= 0)",
                    content = @Content), })
    @Operation(summary = "Get the labseq sequence value at index n")
    @GetMapping("/{n}")
    public ResponseEntity<Map<String, String>> getLabseqValue(@PathVariable(name = "n") Integer n) throws IncorrectParameterValueException {
        LOGGER.info("Received a request on the labseq{} endpoint for calculating the value for N: {}", n, n);

        // startTime and endTime will be used to measure and log the execution time of the call to the algorithm
        long startTime = System.currentTimeMillis();

        BigInteger returnValue = labseqService.getLabseqValue(n);

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime)/1000.0;

        // Creating the response
        Map<String, String> response = new HashMap<>();

        response.put("Execution Time", String.valueOf(duration).concat("s"));
        response.put("Value", String.valueOf(returnValue));

        // Returning the Request Body
        return ResponseEntity.ok().body(response);
    }

}
