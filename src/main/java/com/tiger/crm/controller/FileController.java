package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.file.FileStoreUtils;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import com.tiger.crm.service.board.SystemBoardService;
import com.tiger.crm.service.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Controller
public class FileController {
    /*
     * 파일컨트롤러
     * 작성자 : 제예솔
     * 설명 : 파일 다운로드 및 기타 기능 수행
     * */
    @Autowired
    private ConfigProperties config;
    @Autowired
    private FileService fileService;

    @Autowired
    private FileStoreUtils fileStoreUtils;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    //application.yml 에 파일 위치 명시되어 있음
    @Value("${file.dir}")
    private String fileDir;
    
    //파일 다운로드
    @GetMapping("/fileDownload")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) {
        UploadFileDto file = fileService.getFileByFileName(fileName);
        if (file == null) {
            throw new RuntimeException("File not found in database: " + fileName);
        }
        String downloadFileName = file.getOriginFileName();
        String downloadFilepath = Paths.get(file.getFilePath(), file.getFileName()).toString();
        try {
            // 파일 존재 여부 확인
            File fileOnDisk = new File(downloadFilepath);
            if (!fileOnDisk.exists() || !fileOnDisk.canRead()) {
                LOGGER.error("File not found or cannot be read: {}", downloadFilepath);
                throw new RuntimeException("File not found or cannot be read");
            }
            UrlResource resource = new UrlResource(fileOnDisk.toURI());
            if (!resource.exists()) {
                LOGGER.error("Resource does not exist: {}", resource);
                throw new RuntimeException("File resource does not exist");
            }
            // 파일 이름 인코딩 및 응답 헤더 설정
            String encodedUploadFileName = UriUtils.encode(downloadFileName, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"; filename*=UTF-8''" + encodedUploadFileName;

            LOGGER.info("File ready for download: {}", downloadFileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);

        } catch (MalformedURLException e) {
            LOGGER.error("Invalid file URL", e);
            throw new RuntimeException("Invalid file URL", e);
        }
    }
}
