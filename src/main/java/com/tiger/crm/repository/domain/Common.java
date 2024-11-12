package com.tiger.crm.repository.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Common {

	private Date createDt;
	private String createId;
	private Date updateDt;
	private String updateId;

	public Common() {
		super();
		this.createDt = new Date();
		this.updateDt = new Date();
	}

	public Common(String userId) {
		super();
		this.createId = userId;
		this.createDt = new Date();
		this.updateId = userId;
		this.updateDt = new Date();
	}

}
