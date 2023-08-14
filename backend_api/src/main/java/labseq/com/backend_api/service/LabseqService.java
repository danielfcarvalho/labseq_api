package labseq.com.backend_api.service;

import labseq.com.backend_api.exceptions.IncorrectParameterValueException;
import org.springframework.stereotype.Service;

@Service
public class LabseqService {
    public Integer getLabseqValue(Integer n){
        if(n < 0){
            throw new IncorrectParameterValueException("N should be bigger than 0!");
        }

        return 2;
    }
}
