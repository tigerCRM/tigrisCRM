package com.tiger.crm.repository.dto.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
public class TicketDto {
    private String searchKeyword;
    private String searchStatus;
    private String searchType;
    ;
    private String startDt;
    private String endDt;
    //필터 기간
    private Date filterStartDt;
    private Date filterEndDt;
    //페이지
    private Integer page				= 0;
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