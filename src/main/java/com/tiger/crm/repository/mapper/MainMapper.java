package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainMapper {
    // 메인페이지 요청내역 조회
    List<Map<String, Object>> getMainTicketList(UserLoginDto user);
}