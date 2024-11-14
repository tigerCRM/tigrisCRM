package com.tiger.crm.repository.dto.user;

import lombok.Data;
/*
* 작성자 : 제예솔
* T_USER_INFO 와 매핑하여 사용자 객체 정보 저장
* */
@Data
public class User {

    private String userId;
    private String userName;
    private String password;
    private Integer companyId;
    private String userClass;
    private String userPw;
    //todo : (예솔) 일단은 임시로 만들어 놨는데 제대로 쓸거면 객체 더 추가 해야 함
}
