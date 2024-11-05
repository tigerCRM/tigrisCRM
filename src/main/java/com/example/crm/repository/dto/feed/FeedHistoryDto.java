package com.example.crm.repository.dto.feed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FeedHistoryDto
{
	private String 	feedHistoryId;
	private String 	feedId;
	private String 	userId;
	private String 	historyType;
	private String 	prevState;
	private String 	nextState;
	private String 	comment;
	private Date 	createDt;
	private String 	createId;
	private Date 	updateDt;
	private String 	updateId;
	private String 	userName;
	private String 	jobGrade;
}
