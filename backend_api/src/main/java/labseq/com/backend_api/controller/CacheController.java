package labseq.com.backend_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@Tag(name = "Cache Controller", description = "Endpoints to fetch cache information and statistics")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"0\": \"0\", \"1\": \"1\"}"))),
            })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"REQUESTS\": \"16\", \"HITS\": \"1\", \"SIZE\": \"8\", \"MISSES\": \"15\"}"))),
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"Cache Size\": \"0\"}"))),
    })
    @Operation(summary = "Get the size of the cache")
    @GetMapping("/stats/size")
    @ResponseBody
    public  ResponseEntity<Map<String, Integer>> cacheSize(){
        LOGGER.info("Received a request on the cache endpoint to return current cache size");
        return ResponseEntity.ok().body(cacheService.getCacheSize());
    }

    /**
     * Returns the total number of requests made to the cache.
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"Total Requests\": \"0\"}"))),
    })
    @Operation(summary = "Get the total number of requests made to the cache.")
    @GetMapping("/stats/requests")
    @ResponseBody
    public  ResponseEntity<Map<String, Integer>> cacheRequests(){
        LOGGER.info("Received a request on the cache endpoint to return total cache requests");
        return ResponseEntity.ok().body(cacheService.getCacheRequests());
    }

    /**
     * Returns the total number of hits the cache received.
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"Cache Hits\": \"0\"}"))),
    })
    @Operation(summary = "Get the total number of hits received by the cache.")
    @GetMapping("/stats/hits")
    @ResponseBody
    public  ResponseEntity<Map<String, Integer>>  cacheHits(){
        LOGGER.info("Received a request on the cache endpoint to return total cache hits");
        return ResponseEntity.ok().body(cacheService.getCacheHits());
    }

    /**
     * Returns the total number of misses the cache received.
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"Cache Misses\": \"0\"}"))),
    })
    @Operation(summary = "Get the total number of misses received by the cache.")
    @GetMapping("/stats/misses")
    @ResponseBody
    public  ResponseEntity<Map<String, Integer>>  cacheMisses(){
        LOGGER.info("Received a request on the cache endpoint to return total cache misses");
        return ResponseEntity.ok().body(cacheService.getCacheMisses());
    }
}
