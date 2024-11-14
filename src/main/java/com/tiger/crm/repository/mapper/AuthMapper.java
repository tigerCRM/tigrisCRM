package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    // 메뉴(url) 권한 조회
    String findAuthCodeByMemberId(String requestUrl, String userClass);
}
