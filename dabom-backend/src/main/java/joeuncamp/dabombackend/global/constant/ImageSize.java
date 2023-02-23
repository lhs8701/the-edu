package joeuncamp.dabombackend.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageSize {
    SMALL(0.3f, "s"),
    MEDIUM(0.5f, "m");

    private final double ratio;
    private final String postFix;
}
