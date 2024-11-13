package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    User getUser(String id, String password);

    // 비밀번호 초기화
    void resetPassword(User user);
}
