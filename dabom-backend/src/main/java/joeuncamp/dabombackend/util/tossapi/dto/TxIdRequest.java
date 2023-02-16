package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TxIdRequest {
    String requestType;
    public TxIdRequest(){
        this.requestType = "USER_NONE";
    }
}
