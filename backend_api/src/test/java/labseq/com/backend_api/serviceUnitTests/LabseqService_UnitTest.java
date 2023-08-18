package labseq.com.backend_api.serviceUnitTests;

import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit Tests for the Service Class
 */
@ExtendWith(MockitoExtension.class)
class LabseqService_UnitTest {
    @InjectMocks
    private LabseqService labseqService;
    @Mock
    private LabseqCache cache;

    @Test
    void whenGetLabseqValue_withInvalidIndex_thenThrowException(){
        // Verifying the result is as expected
            assertThatThrownBy(() -> {
                labseqService.getLabseqValue(-5);
            }).isInstanceOf(IncorrectParameterValueException.class);
    }

    @Test
    void whenGetLabseqValue_withValidIndex_thenReturnCorrectValue(){
        // Verify the result is as expected
        assertThat(labseqService.getLabseqValue(13)).isEqualTo(5);
    }

}
