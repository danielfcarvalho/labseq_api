package labseq.com.backend_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("labseq")
@CrossOrigin
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
    @Operation(summary = "Get the labseq sequence value at index n")
    @GetMapping("/{n}")
    public ResponseEntity<Map<String, String>> getLabseqValue(@PathVariable(name = "n") Integer n) throws IncorrectParameterValueException {
        LOGGER.info("Received a request on the labseq{} endpoint for calculating the value for N: {}", n, n);

        // startTime and endTime will be used to measure and log the execution time of the call to the algorithm
        long startTime = System.currentTimeMillis();

        BigInteger returnValue = labseqService.getLabseqValue(n);

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime)/1000.0;
        LOGGER.debug("getLabseqValue execution time for N ({}) was: {}s", n, String.format("%.4fs", duration));

        // Creating the response
        Map<String, String> response = new HashMap<>();

        response.put("Execution Time", String.valueOf(duration));
        response.put("Value", String.valueOf(returnValue).concat("s"));

        // Returning the Request Body
        return ResponseEntity.ok().body(response);
    }

}
