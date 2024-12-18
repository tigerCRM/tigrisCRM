package com.tiger.crm.common.file;

import com.tiger.crm.repository.dto.file.UploadFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
* 파일 저장 관련 기능들
* 작성자 : 제예솔
* 설명 : 첨부파일 업로드 기능 지원
* */
@Component
public class FileStoreUtils {
    
    //application.yml 에 파일 위치 명시되어 있음
    @Value("${file.dir}")
    private String fileDir;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    //파일 전체 경로 찾기
    public String getFullPath(String loc){
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 전체 경로 생성
        String fullPath = fileDir + "\\" + loc + "\\" + today;
        Path path = Paths.get(fullPath);
        try {
            // 경로가 존재하지 않으면 폴더 생성
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create directory: " + fullPath, e);
        }
        return fullPath;
    }
    
    //여러개의 파일 동시 저장
    public List<UploadFileDto> storeFiles(List<MultipartFile> multipartFiles,String loc){
        if(multipartFiles.isEmpty()){
            return null;
        }
        List<UploadFileDto> storeFileList = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                storeFileList.add(storeFile(multipartFile,loc));
            }
        }
        return storeFileList;
    }

    //파일 저장
    public UploadFileDto storeFile(MultipartFile multipartFile,String loc){
        if(multipartFile.isEmpty()){
            return null;
        }

        String originFileName = multipartFile.getOriginalFilename();
        String fileName = createFileName(originFileName);
        try {

            LOGGER.info("Generated file name: {}", fileName);
            String fullPath = Paths.get(getFullPath(loc), fileName).toString();
            LOGGER.info("Saving file to path: {}", fullPath);
            multipartFile.transferTo(new File(fullPath));
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
