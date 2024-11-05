package com.example.crm.repository.dto.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CommunityDto {

	private String 	communityId;
	private String 	creatorId;
	private Date 	createDt = new Date();
	private String 	updateId;
	private Date 	updateDt = new Date();

}
