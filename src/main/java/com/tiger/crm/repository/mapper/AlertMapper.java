package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertMapper {

    // 알림 목록 조회
    List<AlertDto> getAlertList(UserLoginDto loginUser);

}