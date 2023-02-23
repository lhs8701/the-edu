package joeuncamp.dabombackend.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.post.entity.Post;
import joeuncamp.dabombackend.domain.post.entity.Reply;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public class ReplyDto {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class WriteRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long postId;
        @NotBlank
        String content;
        public Reply toEntity(CreatorProfile creator, Post post){
            return Reply.builder()
                    .creator(creator)
                    .post(post)
                    .content(this.content)
                    .build();
        }
    }
    @Getter
    public static class Response{
        Long id;
        String content;
        String writer;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "생성 시간", example = ExampleValue.Time.DATE)
        LocalDateTime createdTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "최근 수정 시간", example = ExampleValue.Time.DATE)
        LocalDateTime modifiedTime;

        public Response(Reply reply){
            this.id = reply.getId();
            this.content = reply.getContent();
            this.writer = reply.getCreator().getMember().getMemberPrivacy().getName();
            this.createdTime = reply.getCreatedTime();
            this.modifiedTime = reply.getModifiedTime();
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        @Schema(hidden = true)
        Long memberId;
        @Schema(hidden = true)
        Long replyId;
        @NotBlank
        String content;
    }
}
