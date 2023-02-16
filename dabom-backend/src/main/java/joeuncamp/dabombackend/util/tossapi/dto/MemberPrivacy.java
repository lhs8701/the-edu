package joeuncamp.dabombackend.util.tossapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPrivacy {
    @Schema(description = "이름", example = "김철수")
    String userName;
    @Schema(description = "전화번호", example = "01012345678")
    String userPhone;
    @Schema(description = "생년월일", example = "19990101")
    String userBirthday;

    /**
     * 인자로 넘어온 개인정보와 동일한지 검증합니다.
     *
     * @param memberPrivacy 개인정보
     * @return true/false
     */
    public boolean match(MemberPrivacy memberPrivacy) {
        return this.userName.equals(memberPrivacy.getUserName()) &&
                this.userPhone.equals(memberPrivacy.getUserPhone()) &&
                this.userBirthday.equals(memberPrivacy.getUserBirthday());
    }
}
