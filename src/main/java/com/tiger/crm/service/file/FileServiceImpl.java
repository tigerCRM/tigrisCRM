package com.tiger.crm.service.file;

import com.tiger.crm.repository.dto.file.UploadFileDto;
import com.tiger.crm.repository.mapper.FileMapper;
import com.tiger.crm.repository.mapper.SystemBoardMapper;
import com.tiger.crm.service.board.SystemBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    //application.yml 에 파일 위치 명시되어 있음
    @Value("${file.dir}")
    private String fileDir;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private SystemBoardService systemBoardService;

    @Override
    public void insertFile(List<UploadFileDto> uploadFiles, int savedBoardId) {

        if(uploadFiles == null){
            return;
        }
        for(int i = 0 ; i < uploadFiles.size();i++){
            UploadFileDto uploadFile = new UploadFileDto(uploadFiles.get(i).getOriginFileName(),uploadFiles.get(i).getFileName());
            uploadFile.setFileId("B" + savedBoardId);
            uploadFile.setSeq(i+1);
            uploadFile.setCategory("시스템관리");
            uploadFile.setFilePath(fileDir);

            fileMapper.insertFile(uploadFile);
            systemBoardService.setSystemBoardFileId(uploadFile.getFileId(),savedBoardId);
        }


    }
}
