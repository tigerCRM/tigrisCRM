package com.tiger.crm.common.file;

import com.tiger.crm.repository.dto.file.UploadFileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
* 파일 저장 관련 기능들
* 작성자 : 제예솔
* */
@Component
public class FileStoreUtils {
    
    //application.yml 에 파일 위치 명시되어 있음
    @Value("${file.dir}")
    private String fileDir;
    
    //파일 전체 경로 찾기
    public String getFullPath(String filename){
        return fileDir + filename;
    }
    
    //여러개의 파일 동시 저장
    public List<UploadFileDto> storeFiles(List<MultipartFile> multipartFiles){
        List<UploadFileDto> storeFileList = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                storeFileList.add(storeFile(multipartFile));
            }
        }
        return storeFileList;
    }

    //파일 저장
    public UploadFileDto storeFile(MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            return null;
        }

        String originFileName = multipartFile.getOriginalFilename();
        String fileName = createFileName(originFileName);
        try {
            multipartFile.transferTo(new File(getFullPath(fileName)));
            return new UploadFileDto(originFileName,fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //UUID 를 이용해 첨부파일 저장명 생성
    private String createFileName(String originFileName){
        String ext = extractExt(originFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //원래 파일명에서 확장자 추출
    private String extractExt(String originFileName){
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos + 1 );
    }

}