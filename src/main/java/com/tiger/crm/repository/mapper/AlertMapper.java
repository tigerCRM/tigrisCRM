package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertMapper {

    // 알림 생성
    void createAlert(AlertDto alertDto);

    // 알림 목록 조회
    List<AlertDto> getAlertList(UserLoginDto loginUser);

    // 알림 갯수 조회
    int getAlertCnt(UserLoginDto loginUser);

    // 알림 읽음 처리
    void updateAlertReadStatus(String alertId);

}
