package joeuncamp.dabombackend.global.constant;

import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StaticFilePath {
    DEFAULT_PROFILE_IMAGE("/static/images/default/profile_image.jpg");
    final String url;
}
