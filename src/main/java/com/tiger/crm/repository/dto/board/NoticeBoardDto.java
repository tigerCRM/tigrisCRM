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
    //게시판번호
    private int boardId;
    //게시판 카테고리
    private String categoryCd;
    private String companyId; //이거 지워도 될지 고민중
    private String companyName;//이거 지워도 될지 고민중
    //열람제한
    private String openYn;
    //상단고정여부
    private String topYn;
    //팝업여부
    private String popupYn;
    //팝업시작일자
    private String popupStartDt;
    //팝업종료일자
    private String popupEndDt;
    //제목
    private String title;
    //내용
    private String content;
    //작성자아이디
    private String createId;
    //작성자명
    private String userName;
    //작성일
    private Date createDt;
    //수정자명
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
