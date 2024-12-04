package com.tiger.crm.service.alert;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;

import java.util.List;

public interface AlertService {

    // 알림 목록 조회
    List<AlertDto> getAlertList(UserLoginDto loginUser);

}
