package com.tiger.crm.service.alert;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertMapper alertMapper;

    // 알림 목록 조회
    @Override
    public List<AlertDto> getAlertList(UserLoginDto loginUser) {
        List<AlertDto> alertLists = alertMapper.getAlertList(loginUser);
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

}
