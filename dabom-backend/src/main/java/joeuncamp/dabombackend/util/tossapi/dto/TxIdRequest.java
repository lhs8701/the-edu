package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TxIdRequest {
    String requestType;
    String triggerType;
    String userName;
    String userPhone;
    String userBirthday;
    String sessionKey;

    @Builder
    public TxIdRequest(String userName, String userPhone, String userBirthday, String sessionKey){
        this.requestType = "USER_PERSONAL";
        this.triggerType = "APP_SCHEME";
        this.userName = userName;
        this.userPhone = userPhone;
        this.userBirthday = userBirthday;
        this.sessionKey = sessionKey;
    }
}
