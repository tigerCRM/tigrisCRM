package com.tiger.crm.repository.dto.user;

import lombok.Data;
/*
* 작성자 : 제예솔
* 설명 : T_USER_INFO 와 매핑하여 사용자 객체 정보 저장
* */
@Data
public class UserLoginDto {
    //유저아이디
    private String userId;
    //유저이름
    private String userName;
    //비밀번호
    private String userPw;
    //소속회사아이디
    private Integer companyId;
    //소속회사명
    private String companyName;
    //권한
    private String userClass;

    //todo : (예솔) 일단은 임시로 만들어 놨는데 제대로 쓸거면 객체 더 추가 해야 함
}
