package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TFileDownloadHistory extends Common {

	private String 	fileDownloadId;
	private String 	userId;
	private String 	fileId;
	private int 	seq;
	private String 	downloadIp;
	private String 	userAgent;

	public TFileDownloadHistory() {
		super();
		this.fileDownloadId = TigrisGlobal.generateID();
	}

	public TFileDownloadHistory(String userId) {
		super(userId);
		this.fileDownloadId = TigrisGlobal.generateID();
	}
}
