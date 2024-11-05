package com.example.crm.repository.dto.feed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FeedUpdateDto
{
	private String 	feedId;
	private String 	communityId;

	private String 	title;
	private String 	requestContents;
	private String 	requestFileId;
	private String 	reviewContents;
	private String 	reviewFileId;
	private String 	feedState;

	private String 	deleteYn;
	private Date 	updateDt = new Date();
	private String 	updateId;

	public FeedUpdateDto()
	{
		this.updateDt = new Date();
	}

	public FeedUpdateDto(String userId)
	{
		this.updateDt = new Date();
		this.updateId = userId;
	}
}
