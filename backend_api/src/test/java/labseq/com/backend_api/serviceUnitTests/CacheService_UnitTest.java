package labseq.com.backend_api.serviceUnitTests;

import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.service.CacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CacheService_UnitTest {
    @Mock
    private LabseqCache cache;
    @InjectMocks
    private CacheService cacheService;

    @Test
    void getCorrectCacheSize() {
        // Set up Expectations
        Map<Integer, BigInteger> fakeCachedResults = new HashMap<>();
        fakeCachedResults.put(0, new BigInteger("0"));
        fakeCachedResults.put(10, new BigInteger("3"));

        when(cache.getCache()).thenReturn(fakeCachedResults);

        // Verify the result is as expected
        assertThat(cacheService.getCacheSize()).containsEntry("Cache Size", 2);

        // Verify that the external API was called and Verify that the cache was called twice - to query and to add the new record
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCache();
    }

    @Test
    void getCorrectHits() {
        // Set up Expectations
        when(cache.getHITS()).thenReturn(5);

        // Verify the result is as expected
        assertThat( cacheService.getCacheHits()).containsEntry("Cache Hits", 5);

        // Verify that the external API was called and Verify that the cache was called twice - to query and to add the new record
        Mockito.verify(cache, VerificationModeFactory.times(1)).getHITS();
    }

    @Test
    void getCorrectMisses() {
        // Set up Expectations
        when(cache.getMISSES()).thenReturn(5);

        // Verify the result is as expected
        assertThat( cacheService.getCacheMisses()).containsEntry("Cache Misses", 5);

        // Verify that the external API was called and Verify that the cache was called twice - to query and to add the new record
        Mockito.verify(cache, VerificationModeFactory.times(1)).getMISSES();
    }

    @Test
    void getCorrectRequests() {
        // Set up Expectations
        when(cache.getTotalRequests()).thenReturn(5);

        // Verify the result is as expected
        assertThat( cacheService.getCacheRequests()).containsEntry("Total Requests", 5);

        // Verify that the external API was called and Verify that the cache was called twice - to query and to add the new record
        Mockito.verify(cache, VerificationModeFactory.times(1)).getTotalRequests();
    }

    @Test
    void getAllStats() {
        // Set up Expectations
        Map<Integer, BigInteger> fakeCachedResults = new HashMap<>();
        fakeCachedResults.put(0, new BigInteger("0"));
        fakeCachedResults.put(10, new BigInteger("3"));

        when(cache.getMISSES()).thenReturn(5);
        when(cache.getHITS()).thenReturn(5);
        when(cache.getCache()).thenReturn(fakeCachedResults);
        when(cache.getTotalRequests()).thenReturn(10);

        Map<String, Integer> expectedStats = new HashMap<>();
        expectedStats.put("REQUESTS", 10);
        expectedStats.put("HITS", 5);
        expectedStats.put("SIZE", 2);
        expectedStats.put("MISSES", 5);

        // Verify the result is as expected
        assertThat( cacheService.getAllStats()).isEqualTo(expectedStats);

        // Verify that the external API was called and Verify that the cache was called twice - to query and to add the new record
        Mockito.verify(cache, VerificationModeFactory.times(1)).getTotalRequests();
        Mockito.verify(cache, VerificationModeFactory.times(1)).getMISSES();
        Mockito.verify(cache, VerificationModeFactory.times(1)).getHITS();
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCache();
    }
}
