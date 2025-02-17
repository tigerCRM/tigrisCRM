package com.tiger.crm.repository.dto.ticket;

import com.tiger.crm.repository.dto.file.UploadFileDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentDto {
    private Integer answerId;         // 댓글 ID
    private Integer ticketId;          // 요청 ID
    private String content;            // 댓글 내용
    private String createId;           // 작성자 ID
    private String createName;         // 작성자 이름
    private String createDt;           // 작성일
    private String fileId;             // 파일ID
    private String userClass;          //사용자 권한
    private String statusCd;        //진행상태
    private String alarmYN = "Y";        //알람여부
    private List<UploadFileDto> attachFiles;
}
