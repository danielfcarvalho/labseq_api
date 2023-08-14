package labseq.com.backend_api.boundaryTests;

import labseq.com.backend_api.controller.LabseqController;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the boundary component of the REST API, in this case, the LabseqController, using the WebMvcTest mode, with @MockBean mocking
 * the access to services
 */
@WebMvcTest(controllers = LabseqController.class)
public class LabseqController_withMockServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabseqService labseqService;

    @Test
    void whenGetLabseqValue_withInvalidIndex_thenReturnStatusNotFound() throws Exception {
        when(labseqService.getLabseqValue(-10)).thenThrow(new IncorrectParameterValueException("N should be bigger than 0!"));

        mockMvc.perform(
                        get("/labseq/-10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetLabseqValue_withValidIndex_thenReturnStatusOK() throws Exception {
        when(labseqService.getLabseqValue(10)).thenReturn(3);

        mockMvc.perform(
                        get("/labseq/10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }
}
