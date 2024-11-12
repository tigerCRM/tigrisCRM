package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TFeed extends Common{

	private String 	feedId;
	private String 	upFeedId;
	private String 	communityId;
	private String 	communityName;
	private String 	userId;
	private String 	userName;
	private String 	orgName;
	private String 	jobgrade;
	private String 	position;
	private String 	teamLeaderId;
	private String 	teamLeaderNm;
	private String 	title;
	private String 	contractPartner;
	private String 	requestContents;
	private String 	reviewContents;
	private String 	replyContents;
	private Date 	requestDate;
	private Date 	reviewDate;
	private String 	requestDateStr;
	private String 	reviewDateStr;
	private String 	requestFileId;
	private String 	reviewFileId;
	private String 	replyFileId;
	private String 	prevFeedState;
	private String 	feedState;
	private String 	feedStateName;
	private String 	replyYn = "N";
	private String 	userAgent;
	private String 	deleteYn = "N";
	private String 	createDtStr;
	private String 	updateDtStr;
	private String 	reviewerName;

	private List<TFile> replyFileList;

	public TFeed() {
		super();
		this.feedId = TigrisGlobal.generateID();
	}

	public TFeed(String userId) {
		super(userId);
		this.feedId = TigrisGlobal.generateID();
	}

}
