package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/*
* FileMapper
* 작성자 : 제예솔
* 첨부파일 및 파일 관련 DB 처리
* */
@Mapper
public interface FileMapper {

    //첨부파일 정보 저장
    void insertFile(UploadFileDto uploadFileDto);
    
    //첨부파일 아이디로 첨부파일 찾아오기
    List<UploadFileDto> getFiles(String fileId);

    //첨부파일 이름으로 첨부파일 찾아오기
    UploadFileDto getFileByFileName(String fileName);

    //첨부파일 아이디로 첨부파일 복수 삭제
    void deleteFilesByFileId(String fileId);
    
    //파일 이름으로 파일 단건 삭제
    void deleteFileByFileName(String fileName);

    //첨부파일 아이디로 제일 마지막 시퀀스 가져오기
    Integer getLastSequenceByFileId(String fileId);
}
