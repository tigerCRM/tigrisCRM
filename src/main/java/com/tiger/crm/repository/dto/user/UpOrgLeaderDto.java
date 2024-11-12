package com.tiger.crm.repository.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpOrgLeaderDto
{
	private String teamLeaderId;
	private String orgCode;
	private String upOrgCode;
	private String chiefStaffNo;
	private String chiefStaffNm;
}
