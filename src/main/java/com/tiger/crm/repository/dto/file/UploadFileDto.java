package com.tiger.crm.repository.dto.file;

import lombok.Data;
/*
* UploadFileDto
* 작성자 : 제예솔
* */
@Data
public class UploadFileDto {
    private String fileId; //파일 아이디
    private int seq;//파일 시퀀스
    private String category; //카테고리
    private String filePath; //파일 저장 경로
    private String fileName; //저장되는 이름(UUID)
    private String originFileName; // 유저가 업로드 한 이름
    private int fileSize; //파일 용량
    private char deleteYn; // 삭제여부

    public UploadFileDto(String originFileName, String fileName){
        this.originFileName = originFileName;
        this.fileName = fileName;
    }
}
