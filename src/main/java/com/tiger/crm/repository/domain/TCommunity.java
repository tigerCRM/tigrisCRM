package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TCommunity extends Common{

	private String communityId;
	private String communityName;
	private String form;
	private String signatureYn 	= "Y";
	private String deleteYn 	= "N";
	private String storageYn 	= "N";

	public TCommunity() {
		super();
		this.communityId = TigrisGlobal.generateID();
	}

	public TCommunity(String userId) {
		super(userId);
		this.communityId = TigrisGlobal.generateID();
	}

}
