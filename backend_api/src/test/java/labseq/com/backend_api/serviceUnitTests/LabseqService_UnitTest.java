package labseq.com.backend_api.serviceUnitTests;

import labseq.com.backend_api.cache.LabseqCache;
import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import labseq.com.backend_api.service.LabseqService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
    void whenGetLabseqValue_withValidIndex_valueInCache_thenCheckCache_andReturnCorrectValue(){
        // Set up Expectations
        when(cache.requestValueFromCache(13)).thenReturn(Optional.of(new BigInteger("5")));

        // Verify the result is as expected
        assertThat(labseqService.getLabseqValue(13)).isEqualTo(5);

        // Verify that the Cache was hit
        this.verifyCacheQueryCalled(13);
    }

    @Test
    void whenGetLabseqValue_withValidIndex_valueNotInCache_thenAddToCache_andReturnCorrectValue(){
        // Set up Expectations
        when(cache.requestValueFromCache(13)).thenReturn(Optional.empty());

        // Verify the result is as expected
        assertThat(labseqService.getLabseqValue(13)).isEqualTo(5);

        // Verify that the new calculated value was added to the cache, as well as intermediary operations, in this case, 10 and 9
        this.verifyCacheAddRecordCalled(13);
        this.verifyCacheAddRecordCalled(9);
        this.verifyCacheAddRecordCalled(10);
    }

    private void verifyCacheQueryCalled(Integer key) {
        Mockito.verify(cache, times(1)).requestValueFromCache(key);
    }

    private void verifyCacheAddRecordCalled(Integer key){
        Mockito.verify(cache, times(1)).addValueToCache(Mockito.eq(key), Mockito.any());
    }

}
