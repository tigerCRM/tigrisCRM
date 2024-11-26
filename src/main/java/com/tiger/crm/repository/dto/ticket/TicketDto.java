package com.tiger.crm.repository.dto.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
public class TicketDto {
    private String searchKeyword;
    private String searchStatus;
    private String searchType;
    private String totalcnt;
    private String startDt;
    private String endDt;
    //필터 기간
    private Date filterStartDt;
    private Date filterEndDt;
    //페이지
    private Integer page				= 0;
    private int limitStart 			= 0;
    private int recordCountPerPage 	= 12;

    private String title;  //제목
    private String content;  //내용
    private String createId;   //작성자ID
    private String managerName;  //담당자이름
    private String managerId;  //담당자ID
    private String companyName;  //고객사명
    private String companyId;  //고객사code
    private String completeDt; //실제완료일
    private String priority;  //중요도
    private String md;        //작업공수
    private String createDt;
    private String updateId;
    private String updateDt;
    private char deleteYn;
    private String fileId;
    private Integer ticketId;           // TICKET_ID
    private String statusCd;             // STATUS_CD
    private String requestTypeCd;        // REQUEST_TYPE_CD
    private LocalDate expectedCompleteDt; // EXPECTED_COMPLETE_DT
    private LocalDate realCompleteDt;    // REAL_COMPLETE_DT
    private String priorityYn;           // PRIORITY_YN
    private String parentTicketCd;       // PARENT_TICKET_CD
    private String supportCd;            // SUPPORT_CD

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