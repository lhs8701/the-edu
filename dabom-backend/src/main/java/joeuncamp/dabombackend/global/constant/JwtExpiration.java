package joeuncamp.dabombackend.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpiration {

      /* 정상 */
//    ACCESS_TOKEN(60 * 30L), //30분
//    REFRESH_TOKEN(60 * 60 * 24 * 7L); //7일

    /* 테스트용 */
    ACCESS_TOKEN(60 * 60 * 24 * 365L), //1년
    REFRESH_TOKEN(60 * 60 * 24 * 365L); //1년

    private final Long time;
}
