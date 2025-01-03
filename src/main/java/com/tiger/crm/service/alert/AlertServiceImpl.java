package com.tiger.crm.service.alert;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.alert.AlertType;
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

    // 알림 모두 읽음 처리
    @Override
    public void updateAllAlertsReadStatus(String userId) { alertMapper.updateAllAlertsReadStatus(userId); }

    // 알림 삭제 처리
    @Override
    public void deleteAlertStatus(String alertId) {
        alertMapper.deleteAlertStatus(alertId);
    }

    // 알림 발송
    @Override
    public void sendAlert(AlertType type , String alertType, int objectId, String content, String senderId, String receiverId) {
        AlertDto alertDto = new AlertDto();     // 알림 객체 생성
        alertDto.setBoardType(type.getType());  // 게시물 타입
        alertDto.setAlertType(alertType);       // 상태코드
        alertDto.setAlertObjectId(objectId);    // 요청사항 고유번호
        alertDto.setContent(content);           // 내용

        switch (type){ // 요청내역, 게시판, 댓글 분류
            case TICKET_STATUS:
                // [ OPEN, CLOSED : 담당자에게 발송]
                // [ RECEIPT, PROGRESS , REVIEW : 작성자에게 발송]
                if(alertType.equals("RECEIPT") || alertType.equals("PROGRESS") || alertType.equals("REVIEW")){
                    alertDto.setSenderId(receiverId);   // 발송인 아이디
                    alertDto.setReceiverId(senderId);   // 수령인 아이디
                }else if(alertType.equals("OPEN") || alertType.equals("CLOSED")){
                    alertDto.setSenderId(senderId);     // 발송인 아이디
                    alertDto.setReceiverId(receiverId); // 수령인 아이디
                }else if(alertType.equals("COMMENT")){  // 댓글일 경우
                    alertDto.setSenderId(senderId);     // 발송인 아이디
                    alertDto.setReceiverId(receiverId); // 수령인 아이디
                }
                break;
            case TICKET_COMMENT:
                // 항상 상대방에게 보냄
                // (ex : 요청자가 댓글 달면 > 담당자에게 발송)
                alertDto.setSenderId(senderId);      // 발송인 아이디
                alertDto.setReceiverId(receiverId);  // 수령인 아이디
                break;
            case NOTICE:

                break;
            default:
                LOGGER.error("error : [type]이 정의되지 않았습니다.");
        }

        try {
            // 알림 발송 (DB 저장 등)
            alertMapper.createAlert(alertDto);
        } catch (Exception e){
            throw new CustomException("알림 등록 중 오류가 발생했습니다.", e);
        }
    }

}
