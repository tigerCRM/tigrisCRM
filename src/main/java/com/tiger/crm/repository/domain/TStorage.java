package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TStorage extends Common{

	private String 	storageId;
	private String 	userId;
	private String 	userName;
	private String 	jobGrade;
	private String 	orgName;
	private String 	title;
	private String 	contents;
	private String 	fileId;
	private String 	userAgent;
	private String 	deleteYn = "N";
	private String 	createDtStr;
	private String 	updateDtStr;

	private List<TFile> replyFileList;

	public TStorage() {
		super();
		this.storageId = TigrisGlobal.generateID();
	}

	public TStorage(String userId) {
		super(userId);
		this.storageId = TigrisGlobal.generateID();
	}

}
