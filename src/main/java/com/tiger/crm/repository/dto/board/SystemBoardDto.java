package com.tiger.crm.repository.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
/*
* SystemBoardDto
* 작성자 : 제예솔
* 설명 : 시스템관리 게시판 dto
* */
@Data
public class SystemBoardDto {
    private int boardId;
    private String categoryCd;
    private String companyId;
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
    private List<MultipartFile> attachFiles;

}
