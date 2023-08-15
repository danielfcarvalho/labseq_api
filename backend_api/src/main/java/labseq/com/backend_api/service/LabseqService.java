package labseq.com.backend_api.service;

import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LabseqService {
    private static final Logger logger = LoggerFactory.getLogger(LabseqService.class);

    /**
     * Calculates the labseq value for a given index. Using the @Cacheable annotation, the Spring Boot application context will automatically
     * check the instantiated Caffeine Cache to see if the value for the called index has already been stored there. In case it has, it will return
     * it instead of executing the method. Otherwise, the method is executed and the output stored in the cache, using N has a key.
     * @param n - The index of the labseq sequence to be fetched
     * @return the value for index N
     */
    public Integer getLabseqValue(Integer n){
        logger.info("Calculating the labseq value for N: {}", n);

        // In case N is invalid, throw an exception
        if(n < 0){
            throw new IncorrectParameterValueException("The value of N should be bigger than 0!");
        }

        // Terminal cases
        if(n == 0 || n == 2){
            return 0;
        } else if (n == 1 || n == 3) {
            return 1;
        }

        // Recursive calls
        int firstTerm, secondTerm, result;

        firstTerm = getLabseqValue(n-4);
        secondTerm = getLabseqValue(n-3);

        result = firstTerm + secondTerm;
        logger.info("Retrieved the the labseq value for N ({}), which is: {}", n, result);
        return result;
    }
}
