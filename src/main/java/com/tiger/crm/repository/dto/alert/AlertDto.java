package com.tiger.crm.repository.dto.alert;

import lombok.Data;

@Data
public class AlertDto {
    private int alertId;            // 알림 고유 아이디
    private String alertType;       // 알림 타입(공지사항, 요청사항 ..)
    private int alertObjectId;   // 알림 연결 객체(요청사항id, 공지사항id)
    private String content;         // 알림 내용
    private String senderId;        // 알림 발송인
    private String receiverId;      // 알림 수령인
    private String alertDt;         // 알림 생성일
    private String readYn;          // 알림 읽음 여부

    private String userName;        // 발송인 이름
}
