package labseq.com.backend_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("labseq")
@CrossOrigin
public class LabseqController {
    private static final Logger logger = LoggerFactory.getLogger(LabseqController.class);
    LabseqService labseqService;

    public LabseqController(LabseqService labseqService) {
        this.labseqService = labseqService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(){
        return ResponseEntity.ok().body("Hello World");
    }

    /**
     * GET endpoint that fetches the labseq sequence value at a specified index
     * @param n - The index of the sequence to be fetched
     * @return a HTTP STATUS OK with the corresponding value
     * @throws IncorrectParameterValueException - In case N < 0, an exception is thrown and a HTTP STATUS NOT FOUND is returned
     */
    @Operation(summary = "Get the labseq sequence value at index n")
    @GetMapping("/{n}")
    public ResponseEntity<BigInteger> getLabseqValue(@PathVariable(name = "n") Integer n) throws IncorrectParameterValueException {
        logger.info("Received a request on the labseq{} endpoint for calculating the value for N: {}", n, n);

        // startTime and endTime will be used to measure and log the execution time of the call to the algorithm
        long startTime = System.currentTimeMillis();

        BigInteger returnValue = labseqService.getLabseqValue(n);

        long endTime = System.currentTimeMillis();
        logger.debug("getLabseqValue execution time for N ({}) was: {}s", n, String.format("%.4fs", (endTime - startTime)/1000.0));

        // Returning the Request Body
        return ResponseEntity.ok().body(returnValue);
    }

}
