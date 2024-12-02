package com.tiger.crm.service.file;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;

import java.util.List;
/*
* FileService
* 작성자 : 제예솔
* 첨부파일 관련 설정
* */
public interface FileService {

    //t_file 에 첨부파일 데이터를 추가
    String insertFile(List<UploadFileDto> uploadFiles, int savedBoardId, String category);

    //첨부파일 리스트 가져오기
    List<UploadFileDto> getFilesById(String type, int id);

    //첨부파일 이름으로 첨부파일 정보 가져오기
    UploadFileDto getFileByFileName(String fileName);

    //첨부파일 복수 삭제
    void deleteFiles(String type, int id);

    //첨부파일 삭제
    void deleteFileByFileName(String fileName);

}
