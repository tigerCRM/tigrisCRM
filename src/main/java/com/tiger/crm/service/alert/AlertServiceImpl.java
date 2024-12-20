package com.tiger.crm.service.alert;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.AlertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertMapper alertMapper;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    // 알림 목록 조회
    @Override
    public List<AlertDto> getAlertList(PagingRequest pagingRequest) {
        List<AlertDto> alertLists = alertMapper.getAlertList(pagingRequest);
        return alertLists;
    }

    // 알림 갯수 조회
    @Override
    public int getAlertCnt(UserLoginDto loginUser) {
        return alertMapper.getAlertCnt(loginUser);
    }

    // 알림 읽음 처리
    @Override
    public void updateAlertReadStatus(String alertId) {
        alertMapper.updateAlertReadStatus(alertId);
    }

    // 알림 삭제 처리
    @Override
    public void deleteAlertStatus(String alertId) {
        alertMapper.deleteAlertStatus(alertId);
    }

    // 알림 발송
    @Override
    public void sendAlert(String type ,String alertType, int objectId, String content, String senderId, String receiverId) {

        // 알림 객체 생성
        AlertDto alertDto = new AlertDto();
        
        // 요청내역,게시판, 댓글 분류
        switch (type){
            case "TICKET":
                alertDto.setBoardType(type);
                alertDto.setAlertType(alertType); // 상태코드

                // [ OPEN, CLOSED : 수신인은 담당자 ], [ RECEIPT, PROGRESS , REVIEW : 수신인 작성자]
                if(alertType.equals("RECEIPT") || alertType.equals("PROGRESS") || alertType.equals("REVIEW")){
                    alertDto.setSenderId(receiverId);  // 발송인 아이디
                    alertDto.setReceiverId(senderId);  // 수령인 아이디
                }else if(alertType.equals("OPEN") || alertType.equals("CLOSED")){
                    alertDto.setSenderId(senderId);     // 발송인 아이디
                    alertDto.setReceiverId(receiverId); // 수령인 아이디
                }
                break;

            case "NOTICE":
                alertDto.setBoardType(type);
                break;

            default:
                LOGGER.error("error : [type]이 정의되지 않았습니다.");
        }
        alertDto.setAlertObjectId(objectId);       // 요청사항 고유번호
        alertDto.setContent(content);              // 내용

        // 알림 발송 (DB 저장 등)
        alertMapper.createAlert(alertDto);
    }

}
