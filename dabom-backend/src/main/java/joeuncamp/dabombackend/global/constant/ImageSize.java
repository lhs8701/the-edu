package joeuncamp.dabombackend.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum ImageSize {
    SMALL(200, "w200"),
    MEDIUM(400, "w400"),
    LARGE(600, "w600");

    private final int pixelSize;
    private final String additionalWord;

    public int getPixelSize() {
        return pixelSize;
    }
}
