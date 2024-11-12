package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TPrintHistory extends Common{

	private String printHistoryId;
	private String userId;
	private String feedId;
	private String userAgent;

	public TPrintHistory() {
		super();
		this.printHistoryId = TigrisGlobal.generateID();
	}

	public TPrintHistory(String userId) {
		super(userId);
		this.userId 		= userId;
		this.printHistoryId = TigrisGlobal.generateID();
	}

}
