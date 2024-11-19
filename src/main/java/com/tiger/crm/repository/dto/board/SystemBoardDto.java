package com.tiger.crm.repository.dto.board;

import lombok.Data;
import java.util.Date;
@Data
public class SystemBoardDto {
    private int boardId;
    private String categoryCd;
    private String companyName;
    private String title;
    private String content;
    private String createId;
    private String userName;
    private Date createDt;
    private String updateId;
    private Date updateDt;
    private char deleteYn;
    private String fileId;

}