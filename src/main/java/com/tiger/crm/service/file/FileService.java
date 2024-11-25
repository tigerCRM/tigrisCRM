package com.tiger.crm.service.file;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;

public interface FileService {

    void insertFile(UploadFileDto uploadFileDto);
}
