package labseq.com.backend_api.service;

import labseq.com.backend_api.cache.LabseqCache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {
    private LabseqCache cache;

    public CacheService(LabseqCache cache) {
        this.cache = cache;
    }

    /**
     * Returns the current size of the cache.
     * @return
     */
    public Map<String, Integer> getCacheSize(){
        int size = cache.getCache().size();
        return Map.of("Cache Size", size);
    }

    /**
     * Returns the total number of requests made to the cache.
     * @return
     */
    public Map<String, Integer> getCacheRequests(){
        int totalRequests = cache.getTotalRequests();
        return Map.of("Total Requests", totalRequests);
    }

    /**
     * Returns the total number of misses the cache received.
     * @return
     */
    public Map<String, Integer> getCacheMisses(){
        int misses = cache.getMISSES();
        return Map.of("Cache Misses", misses);
    }

    /**
     * Returns the total number of hits the cache received.
     * @return
     */
    public Map<String, Integer> getCacheHits(){
        int hits = cache.getHITS();
        return Map.of("Cache Hits", hits);
    }

    /**
     * Gets a map containing all the statistics related with cache uses, including
     * hits, misses, total requests and size.
     * @return
     */
    public Map<String,Integer> getAllStats(){
        Map<String,Integer> allStats = new HashMap<>();

        allStats.put("SIZE", cache.getCache().size());
        allStats.put("REQUESTS", cache.getTotalRequests());
        allStats.put("MISSES", cache.getMISSES());
        allStats.put("HITS", cache.getHITS());

        return allStats;
    }
}
