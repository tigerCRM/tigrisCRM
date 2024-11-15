package com.tiger.crm.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    // 메뉴(url) 권한 조회
    String findAuthCodeByMemberId(String requestUrl, String userClass);
}
