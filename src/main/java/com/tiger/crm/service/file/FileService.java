package com.tiger.crm.service.file;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;

import java.util.List;

public interface FileService {

    void insertFile(List<UploadFileDto> uploadFiles, int savedBoardId);

    List<UploadFileDto> getFilesById(String type, int id);

    UploadFileDto getFileByFileName(String fileName);

    void deleteFiles(String type, int id);

}
