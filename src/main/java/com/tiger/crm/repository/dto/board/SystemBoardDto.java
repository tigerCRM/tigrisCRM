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
    //게시판번호
    private int boardId;
    //카테고리코드
    private String categoryCd;
    //대상회사아이디
    private String companyId;
    //대상회사이름
    private String companyName;
    //제목
    private String title;
    //내용
    private String content;
    //작성자아이디
    private String createId;
    //작성자이름
    private String userName;
    //작성일
    private Date createDt;
    //수정자아이디
    private String updateId;
    //수정일
    private Date updateDt;
    //삭제여부
    private char deleteYn;
    //파일아이디
    private String fileId;
    //첨부파일(멀티파트)
    private List<MultipartFile> attachFiles;

}
