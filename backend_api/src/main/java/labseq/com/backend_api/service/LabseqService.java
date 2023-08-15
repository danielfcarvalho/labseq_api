package labseq.com.backend_api.service;

import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class LabseqService {
    private static final Logger logger = LoggerFactory.getLogger(LabseqService.class);

    private LabseqCache cache;

    public LabseqService(LabseqCache cache) {
        this.cache = cache;
    }

    /**
     * Calculates the labseq value for a given index. It first checks whether the value is already in the Cache or not.
     * In case it's not, it calculates the value based on the labseq formula. If intermediary operations are done,
     * the cache is checked again for each individual value, and is updated once the operations are done.
     * @param n - The index of the labseq sequence to be fetched
     * @return the value for index N
     */
    public BigInteger getLabseqValue(Integer n){
        // In case N is invalid, throw an exception
        if(n < 0){
            throw new IncorrectParameterValueException("The value of N should be bigger than 0!");
        }

        // Check if N is already in the cache
        Optional<BigInteger> cachedValue = cache.requestValueFromCache(n);
        if(cachedValue.isPresent()){
            logger.info("The value for N ({}) was retrieved from the cache.", n);
            return cachedValue.get();
        }

        // Terminal cases
        if (n <= 3) {
            BigInteger result = BigInteger.valueOf(n % 2);
            cache.addValueToCache(n, result);
            logger.info("Calculated the labseq value for N ({}), which is: {}", n, result);
            return result;
        }

        // Calculate for smaller versions first
        if (n > 5000){
            getLabseqValue(n/2);
        }

        // Recursive calls
        BigInteger firstTerm, secondTerm, result;

        // Verifying if the intermediary terms are in the cache or not
        Optional<BigInteger> cachedFirstTerm = cache.requestValueFromCache(n-4);
        Optional<BigInteger> cachedSecondTerm = cache.requestValueFromCache(n-3);

        // Either returning from the cache, or calculating the new value
        firstTerm = cachedFirstTerm.orElseGet(() -> getLabseqValue(n-4));
        secondTerm = cachedSecondTerm.orElseGet(() -> getLabseqValue(n-3));

        result = firstTerm.add(secondTerm);

        // Adding the result to the cache and returning
        cache.addValueToCache(n, result);
        logger.info("Calculated the the labseq value for N ({}), which is: {}", n, result);
        return result;
    }
}
