package com.tiger.crm.service.alert;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertMapper alertMapper;

    // 안읽은 알림 개수 조회
//    @Override
//    public int unReadAlerts(UserLoginDto loginUser) {
//        return alertMapper.unReadAlerts(loginUser);
//    }

    @Override
    public List<AlertDto> getAlertList(UserLoginDto loginUser) {
        List<AlertDto> alertLists = alertMapper.getAlertList(loginUser);

        return alertLists;
    }

    // 메인 페이지 완료내역 조회
//    @Override
//    public List<Map<String, Object>> getCloseList(UserLoginDto user) {
//        List<Map<String, Object>> ticketList = mainMapper.getCloseList(user);
//        return ticketList;
//    }

}
