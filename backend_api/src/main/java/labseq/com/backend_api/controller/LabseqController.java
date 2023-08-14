package labseq.com.backend_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("labseq")
@CrossOrigin
public class LabseqController {
    @Autowired
    LabseqService labseqService;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(){
        return ResponseEntity.ok().body("Hello World");
    }

    @Operation(summary = "Get the labseq sequence value at index n")
    @GetMapping("/{n}")
    public ResponseEntity<Integer> getLabseqValue(@PathVariable(name = "n") Integer n) throws IncorrectParameterValueException {
        return ResponseEntity.ok().body(labseqService.getLabseqValue(n));
    }

}
