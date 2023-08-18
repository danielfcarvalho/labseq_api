package labseq.com.backend_api.boundaryTests;

import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.controller.CacheController;
import labseq.com.backend_api.service.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the boundary component of the REST API, in this case, the CacheController, using the WebMvcTest mode, with @MockBean mocking
 * the access to services
 */
@WebMvcTest(controllers = CacheController.class)
public class CacheController_withMockServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private LabseqCache cache;

    @Test
    public void getCorrectCacheSize() throws Exception {
        // Set up Expectations
        when(cacheService.getCacheSize()).thenReturn(5);

        // Verify the result is as expected
        mockMvc.perform(
                        get("/labseq/cache/stats/size").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }

    @Test
    public void getCorrectHits() throws Exception {
        // Set up Expectations
        when(cacheService.getCacheHits()).thenReturn(5);

        // Verify the result is as expected
        mockMvc.perform(
                        get("/labseq/cache/stats/hits").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }

    @Test
    public void getCorrectMisses() throws Exception {
        // Set up Expectations
        when(cacheService.getCacheMisses()).thenReturn(5);

        // Verify the result is as expected
        mockMvc.perform(
                        get("/labseq/cache/stats/misses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }

    @Test
    public void getCorrectRequests() throws Exception {
        // Set up Expectations
        when(cacheService.getCacheRequests()).thenReturn(5);

        // Verify the result is as expected
        mockMvc.perform(
                        get("/labseq/cache/stats/requests").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }

    @Test
    public void getAllStats() throws Exception {
        // Set up Expectations
        Map<String, Integer> expectedStats = new HashMap<>();
        expectedStats.put("REQUESTS", 10);
        expectedStats.put("HITS", 5);
        expectedStats.put("SIZE", 2);
        expectedStats.put("MISSES", 5);

        when(cacheService.getAllStats()).thenReturn(expectedStats);

        // Verify the result is as expected
        mockMvc.perform(
                        get("/labseq/cache/stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.REQUESTS", is(10)))
                .andExpect(jsonPath("$.HITS", is(5)))
                .andExpect(jsonPath("$.SIZE", is(2)))
                .andExpect(jsonPath("$.MISSES", is(5)));


    }
}
