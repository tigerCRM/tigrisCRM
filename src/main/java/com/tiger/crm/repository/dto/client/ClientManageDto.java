package com.tiger.crm.repository.dto.client;

import lombok.Data;

@Data
public class ClientManageDto {

    // 고객사 정보(웹사이트, 비고는 추후 결정)
    private int companyId;          // 고객사 아이디
    private String companyName;     // 고객사 명
    private String managerId;       // 고객사 담당자
    private String managerName;     // 고객사 담당자 명
    private String notes;           // 고객사 비고(설명)
    private String companyuseYn;           // 고객사 비고(설명)

    // 고객 정보
    private int clientcompanyId;          // 고객사 아이디
    private String clientcompanyName;     // 고객사 명
    private String userId;          // 고객 아이디, 이메일
    private String userName;        // 고객 명
    private String jobClass;        // 고객 직급
    private String userClass;       // 고객 권한
    private String email;           // 고객 메일
    private String phone;           // 고객 전화번호
    private String createId;        // 생성자 아이디
    private String useruseYn;
    private String userPw;
    private String updateId;        // 생성자 아이디
    //그룹정보
    private String groupId;       // 그룹코드
    private String groupCode;       // 그룹코드
    private String groupName;       // 그룹명
    private String groupNotes;      // 그룹 비고(설명)
    private String groupuseYn;
}
