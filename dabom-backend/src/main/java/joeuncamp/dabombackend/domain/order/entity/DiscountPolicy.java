package joeuncamp.dabombackend.domain.order.entity;

import java.util.function.Function;

public enum DiscountPolicy {
    RATE {
        long calculate(long value, long discount){
            return value * (100 - discount / 100);
        }
    };
    abstract long calculate(long value, long discount);
}
