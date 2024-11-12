package com.tiger.crm.repository.dto.feed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedInfoDto
{
	private String feedId;
	private String communityName;
	private String title;
	private String orgName;
	private String userName;
	private String jobgrade;
}
