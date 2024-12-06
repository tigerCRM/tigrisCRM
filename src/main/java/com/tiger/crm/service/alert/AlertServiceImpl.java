package com.tiger.crm.service.alert;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertMapper alertMapper;

    // 알림 생성
    @Override
    public void createAlert(AlertDto alertDto) {
        alertMapper.createAlert(alertDto);
    }

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

}
