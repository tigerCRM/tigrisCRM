package com.example.crm.repository.dto.storage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
public class StorageSearchDto
{
	private String storageId;
	private String userId;

	//필터 구분 - 제목+내용(TITLEANDCONTENTS), 제목(TITLE), 내용(CONTENTS), 작성자(WRITER)
	private String searchType = "TITLEANDCONTENTS";
	private String searchKeyword;

	//필터 기간
	private Date filterStartDt;
	private Date filterEndDt;

	//데이터 개수
	private int page 				= 1;
	private int limitStart 			= 0;
	private int recordCountPerPage 	= 12;

	public void setFilterStartDt(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			this.filterStartDt = sdf.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public void setFilterEndDt(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			this.filterEndDt = sdf.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public int getLimitStart()
	{
		return (this.page - 1) * this.recordCountPerPage;
	}

	public int getLimitEnd()
	{
		return this.limitStart + this.recordCountPerPage;
	}

}
