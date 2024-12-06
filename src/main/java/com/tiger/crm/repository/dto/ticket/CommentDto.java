package com.tiger.crm.repository.dto.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDto {
    private Integer commentId;         // 댓글 ID
    private Integer ticketId;          // 티켓 ID
    private String content;            // 댓글 내용
    private String createId;           // 작성자 ID
    private String createName;         // 작성자 이름
    private String createDt;           // 작성일
    private String fileId;             // 파일ID
    private String userClass;          //사용자 권한
}
