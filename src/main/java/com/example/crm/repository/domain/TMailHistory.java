package com.example.crm.repository.domain;

import com.example.crm.core.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TMailHistory extends Common {

	private String 	mailHistoryId;
	private String 	feedId;
	private String 	userId;
	private String 	senderEmail;
	private String 	receiverName;
	private String 	receiverEmail;
	private String 	title;
	private String 	contents;
	private String 	linkUrl;

	public TMailHistory() {
		super();
		this.mailHistoryId = TigrisGlobal.generateID();
	}

	public TMailHistory(String userId) {
		super(userId);
		this.mailHistoryId = TigrisGlobal.generateID();
	}
}
