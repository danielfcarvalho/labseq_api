package labseq.com.backend_api.cache;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class LabseqCache {
    private int HITS;
    private int MISSES;
    private Map<Integer, BigInteger> cache;

    public LabseqCache() {
        this.HITS = 0;
        this.MISSES = 0;
        this.cache = new HashMap<>();
    }

    public Optional<BigInteger> requestValueFromCache(Integer n){
        // Verify if the requested query is stored in the Cache. If so, and it isn't expired, return it
        if(cache.containsKey(n)){
            this.HITS += 1;
            return Optional.of(cache.get(n));
        }else{
            this.MISSES += 1;
            return Optional.empty();
        }
    }

    public void addValueToCache(Integer n, BigInteger value){
        cache.put(n, value);
    }

    public int getHITS() {
        return HITS;
    }

    public int getMISSES() {
        return MISSES;
    }

    public int getTotalRequests(){
        return HITS + MISSES;
    }

    public Map<Integer, BigInteger> getCache() {
        return cache;
    }
}
