package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainMapper {
    // 메인페이지 요청내역 조회
    List<Map<String, Object>> getMainTicketList(UserLoginDto user);

    // 메인 페이지 완료내역 조회
    List<Map<String, Object>> getCloseList(UserLoginDto user);

    // 공지사항 목록 조회
    List<Map<String, Object>> getNoticeList(UserLoginDto user);
}