package labseq.com.backend_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("labseq/cache")
public class CacheController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);
    private LabseqCache cache;
    private CacheService cacheService;

    public CacheController(LabseqCache cache, CacheService cacheService) {
        this.cache = cache;
        this.cacheService = cacheService;
    }

    /**
     * Gets the full map of key-values stored in the cache
     * @return
     */
    @Operation(summary = "Get all of the values stored in the cache")
    @GetMapping("/all")
    public ResponseEntity<Map<Integer, BigInteger>> getAllCachedValues(){
        LOGGER.info("Received a request on the cache endpoint to return all cached values");
        return ResponseEntity.ok().body(cache.getCache());
    }

    /**
     * Gets a map containing all the statistics related with cache uses, including
     * hits, misses, total requests and size.
     * @return
     */
    @Operation(summary = "Get the complete set of cache statistics")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getFullCacheStats(){
        LOGGER.info("Received a request on the cache endpoint to return all cache statistics");
        return ResponseEntity.ok().body(cacheService.getAllStats());
    }

    /**
     * Returns the current size of the cache.
     * @return
     */
    @Operation(summary = "Get the size of the cache")
    @GetMapping("/stats/size")
    @ResponseBody
    public  ResponseEntity<Integer> cacheSize(){
        LOGGER.info("Received a request on the cache endpoint to return current cache size");
        return ResponseEntity.ok().body(cacheService.getCacheSize());
    }

    /**
     * Returns the total number of requests made to the cache.
     * @return
     */
    @Operation(summary = "Get the total number of requests made to the cache.")
    @GetMapping("/stats/requests")
    @ResponseBody
    public  ResponseEntity<Integer> cacheRequests(){
        LOGGER.info("Received a request on the cache endpoint to return total cache requests");
        return ResponseEntity.ok().body(cacheService.getCacheRequests());
    }

    /**
     * Returns the total number of hits the cache received.
     * @return
     */
    @Operation(summary = "Get the total number of hits received by the cache.")
    @GetMapping("/stats/hits")
    @ResponseBody
    public  ResponseEntity<Integer> cacheHits(){
        LOGGER.info("Received a request on the cache endpoint to return total cache hits");
        return ResponseEntity.ok().body(cacheService.getCacheHits());
    }

    /**
     * Returns the total number of misses the cache received.
     * @return
     */
    @Operation(summary = "Get the total number of misses received by the cache.")
    @GetMapping("/stats/misses")
    @ResponseBody
    public  ResponseEntity<Integer> cacheMisses(){
        LOGGER.info("Received a request on the cache endpoint to return total cache misses");
        return ResponseEntity.ok().body(cacheService.getCacheMisses());
    }
}
