package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LoginMapper {
    //아이디 패스워드로 사용자 조회(로그인)
    UserLoginDto getUser(String id, String password);

    // 비밀번호 초기화
    void resetPassword(UserLoginDto user);

    //전체 사용자 불러오기
    List<UserLoginDto> getAllUsers();
    
    //사용자 비밀번호 업데이트(**주의** 테스트 시 외에는 사용하지 말것)
    void updateUserPassword(String id, String password);

    //사용자 전화번호 업데이트(**주의** 테스트 시 외에는 사용하지 말것)
    void updateUserPhone(String id, String phone);
    
    String getUserPwByUserId(String id);

}
