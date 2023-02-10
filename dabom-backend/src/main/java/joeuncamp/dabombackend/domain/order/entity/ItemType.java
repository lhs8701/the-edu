package joeuncamp.dabombackend.domain.order.entity;

public enum ItemType {
    COURSE("강좌");
    final String title;

    ItemType(String title) {
        this.title = title;
    }
}
