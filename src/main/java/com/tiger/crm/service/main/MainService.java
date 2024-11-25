package com.tiger.crm.service.main;

import com.tiger.crm.repository.dto.user.UserLoginDto;

import java.util.List;
import java.util.Map;


public interface MainService {

    // 메인페이지 요청내역 조회
    List<Map<String, Object>> getMainTicketList(UserLoginDto user);

}


