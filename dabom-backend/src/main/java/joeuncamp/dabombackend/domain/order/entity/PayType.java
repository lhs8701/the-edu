package joeuncamp.dabombackend.domain.order.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PayType {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    ACCOUNT_TRANSFER("계좌이체"),
    MOBILE("휴대폰"),
    EASY("간편결제"),
    EMPTY("없음");
    final String method;

    PayType(String method) {
        this.method = method;
    }

    public static PayType findByMethod(String method){
        return Arrays.stream(values())
                .filter(type -> type.getMethod().equals(method))
                .findAny()
                .orElse(EMPTY);
    }
}
