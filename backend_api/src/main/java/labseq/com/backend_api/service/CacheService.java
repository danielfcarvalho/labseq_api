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
    public int getCacheSize(){
        return cache.getCache().size();
    }

    /**
     * Returns the total number of requests made to the cache.
     * @return
     */
    public int getCacheRequests(){
        return cache.getTotalRequests();
    }

    /**
     * Returns the total number of misses the cache received.
     * @return
     */
    public int getCacheMisses(){
        return cache.getMISSES();
    }

    /**
     * Returns the total number of hits the cache received.
     * @return
     */
    public int getCacheHits(){
        return cache.getHITS();
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
