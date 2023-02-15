package joeuncamp.dabombackend.domain.order.entity;

import java.util.function.Function;

public enum DiscountPolicy {
    RATE {
        long calculate(long value, long discount){
            return value * (100 - discount / 100);
        }
    },
    FIX {
        long calculate(long value, long discount){
            return value - discount;
        }
    };
    abstract long calculate(long value, long discount);
}
