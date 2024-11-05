package com.example.crm.repository.domain;

import com.example.crm.core.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TOrg extends Common{

	private String orgId;
	private String siteId;
	private String orgCode;
	private String upOrgCode;
	private String orgSeq;
	private String orgName;
	private String staYmd;
	private String endYmd;
	private String depthLevel;
	private String corpId;
	private String chiefStaffNo;
	private String chiefStaffNm;
	private String chartYn;

	public TOrg() {
		super();
		this.orgId = TigrisGlobal.generateID();
	}

	public TOrg(String userId) {
		super(userId);
		this.orgId = TigrisGlobal.generateID();
	}

}
