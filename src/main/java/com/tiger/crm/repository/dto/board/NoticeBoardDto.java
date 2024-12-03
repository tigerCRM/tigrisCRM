package com.tiger.crm.repository.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/*
* NoticeBoardDto
* 작성자 : 제예솔
* 설명 : 공지사항 게시판 dto
* */
@Data
public class NoticeBoardDto {
    private int boardId;
    private String categoryCd;
    private String companyId; //이거 지워도 될지?
    private String companyName;//이거 지워도 될지?
    private char openYn;
    private char topYn;
    private char popupYn;
    private Date popupStartDt;
    private Date popupEndDt;
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
