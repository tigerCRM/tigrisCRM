package com.tiger.crm.repository.dto.feed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FeedDeleteDto
{
	private String 	feedId;
	private String 	userId;

	private String 	deleteYn;
	private Date 	updateDt;
	private String 	updateId;

	public FeedDeleteDto()
	{
		this.updateDt = new Date();
	}
}
