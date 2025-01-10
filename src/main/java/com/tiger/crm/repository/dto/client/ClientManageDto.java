package com.tiger.crm.repository.dto.client;

import lombok.Data;

@Data
public class ClientManageDto {

    // 고객사 정보(웹사이트, 비고는 추후 결정)
    private int companyId;          // 고객사 아이디
    private String companyName;     // 고객사 명
    private String managerId;       // 고객사 담당자
    private String managerName;       // 고객사 담당자 명

    // 고객 정보
    private String userId;          // 고객 아이디, 이메일
    private String userName;        // 고객 명
    private String jobClass;        // 고객 직급
    private String userClass;       // 고객 권한
    private String email;           // 고객 메일
    private String phone;           // 고객 전화번호

}
