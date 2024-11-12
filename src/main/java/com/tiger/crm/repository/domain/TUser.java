package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TUser extends Common {

	private String 	userId;
	private String 	siteId;
	private String 	empId;
	private String 	empNo;
	private String 	userName;
	private String 	loginEmail;
	private String 	orgId;
	private String 	orgCode;
	private String 	corpId;
	private String 	empYmd;
	private String 	lockYn;
	private String 	mobile;
	private String 	jobGrade;
	private String 	position;
	private String 	deleteYn = "N";

	public TUser() {
		super();
		this.userId = TigrisGlobal.generateID();
	}

	public TUser(String userId) {
		super(userId);
		this.userId = TigrisGlobal.generateID();
	}
}
