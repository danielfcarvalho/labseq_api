package labseq.com.backend_api.serviceUnitTests;

import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class LabseqService_UnitTest {
    @InjectMocks
    private LabseqService labseqService;

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
        assertThat(labseqService.getLabseqValue(10)).isEqualTo(3);
    }

}
