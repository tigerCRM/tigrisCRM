package com.tiger.crm.service.alert;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.alert.AlertType;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.user.UserLoginDto;

import java.util.List;

public interface AlertService {

    // 알림 목록 조회
    List<AlertDto> getAlertList(PagingRequest pagingRequest);

    // 알림 갯수 조회
    int getAlertCnt(UserLoginDto loginUser);

    // 알림 읽음 처리
    void updateAlertReadStatus(String alertId);

    // 알림 모두 읽음 처리
    void updateAllAlertsReadStatus(String userId);

    // 알림 삭제 처리
    void deleteAlertStatus(String alertId);

    // 알림 발송
    void sendAlert(AlertType type, String alertType, int objectId, String content, String senderId, String receiverId);

}
