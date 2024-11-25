package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    //첨부파일 정보 저장
    void insertFile(UploadFileDto uploadFileDto);
}
