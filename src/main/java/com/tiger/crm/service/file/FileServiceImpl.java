package com.tiger.crm.service.file;

import com.tiger.crm.repository.dto.file.UploadFileDto;
import com.tiger.crm.repository.mapper.FileMapper;
import com.tiger.crm.repository.mapper.SystemBoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService{

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileMapper fileMapper;
    @Override
    public void insertFile(UploadFileDto uploadFileDto) {
        fileMapper.insertFile(uploadFileDto);
    }
}
