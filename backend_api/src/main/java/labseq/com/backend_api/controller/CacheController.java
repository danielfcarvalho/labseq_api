package labseq.com.backend_api.controller;

import labseq.com.backend_api.cache.LabseqCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("labseq/cache")
@CrossOrigin
public class CacheController {
    @Autowired
    private LabseqCache cache;

    @GetMapping("/all")
    public ResponseEntity<Map<Integer, BigInteger>> getAllCachedValues(){
        return ResponseEntity.ok().body(cache.getCache());
    }
}
