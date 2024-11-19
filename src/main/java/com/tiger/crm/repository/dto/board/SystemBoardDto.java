package com.tiger.crm.repository.dto.board;

import lombok.Data;
import java.util.Date;
@Data
public class SystemBoardDto {
    private int boardId;
    private String categoryId;
    private String title;
    private String content;
    private String createId;
    private Date createDt;
    private String updateId;
    private Date updateDt;
    private char deleteYn;
    private String fileId;

}
