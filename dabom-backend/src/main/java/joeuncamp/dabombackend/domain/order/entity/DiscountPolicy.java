package joeuncamp.dabombackend.domain.order.entity;

public enum DiscountPolicy {
    RATE {
        long calculate(long value, long discount) {
            return (long) (value * ((double) discount / 100));
        }
    };

    abstract long calculate(long value, long discount);
}
