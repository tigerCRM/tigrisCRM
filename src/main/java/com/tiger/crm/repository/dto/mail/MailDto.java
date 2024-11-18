package com.tiger.crm.repository.dto.mail;

import java.util.Date;
import lombok.Data;

@Data
public class MailDto {
    private String mailSendId;    // 메일 전송 ID
    private String category;      // 이메일 카테고리
    private String title;         // 제목
    private String content;       // 내용
    private String senderAddr;    // 발신자 주소
    private String receiverAddr;  // 수신자 주소
    private Date sendDt;          // 전송 일시
    private String successYn;     // 성공 여부
}