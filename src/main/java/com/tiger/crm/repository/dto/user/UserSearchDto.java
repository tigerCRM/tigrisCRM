package com.tiger.crm.repository.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserSearchDto
{
	private String userId;          // 아이디
	private String userName;        // 이름
	private String userPw;          // 비밀번호
	private int companyId;          // 고객사번호
	private String userAuth;        // 사용자구분
	private String job;             // 직급
	private String email;           // 이메일주소
	private String phone;           // 핸드폰번호
	private String extensionPhone;   // 회사번호
	private Date createDt;          // 생성일
	private String createId;        // 생성자
	private Date updateDt;          // 수정일
	private String updateId;        // 수정자
	private String useYn;           // 사용여부

	
	// 아래는 기존
	private String orgId;
//	private String searchType;
	private String keyword;
//
//	private String userId;
	private String orgCode;
	private String orgName;
	private String chiefStaffNo;
	private String chiefStaffNm;
	private String loginEmail;
//	private String userName;
	private String jobGrade;
//	private String position;
	private String teamLeaderId;

//	public String toStringForCookie()
//	{
//		return this.userId+":"+this.orgCode+":"+this.orgName+":"+this.chiefStaffNo+":"+this.chiefStaffNm+":"+this.loginEmail+":"+this.userName+":"+this.jobGrade+":"+this.teamLeaderId;
//	}
}
