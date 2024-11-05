package com.example.crm.repository.domain;

import com.example.crm.core.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TFeedHistory extends Common{

	private String 	feedHistoryId;
	private String 	feedId;
	private String 	userId;
	private String 	historyType;
	private String 	prevState;
	private String 	nextState;
	private String 	comment;

	public TFeedHistory() {
		super();
		this.feedHistoryId = TigrisGlobal.generateID();
	}

	public TFeedHistory(String userId) {
		super(userId);
		this.userId 		= userId;
		this.feedHistoryId 	= TigrisGlobal.generateID();
	}

}
