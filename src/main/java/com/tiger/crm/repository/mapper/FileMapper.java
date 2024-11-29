package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    //첨부파일 정보 저장
    void insertFile(UploadFileDto uploadFileDto);
    
    //첨부파일 아이디로 첨부파일 찾아오기
    List<UploadFileDto> getFiles(String fileId);

    //첨부파일 이름으로 첨부파일 찾아오기
    UploadFileDto getFileByFileName(String fileName);

    //첨부파일 아이디로 첨부파일들 삭제
    void deleteFilesByFileId(String fileId);

    void deleteFileByFileName(String fileName);
}
