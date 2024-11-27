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

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
public class FileController {
    /*
     * 파일컨트롤러
     * 작성자 : 제예솔
     * 설명 : 파일 다운로드 용으로 만들었어요
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

    @GetMapping("/fileDownload")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName){

        UploadFileDto file = fileService.getFileByFileName(fileName);

        String uploadFileName = file.getOriginFileName();
        try {
            UrlResource resource = new UrlResource("file:" + fileStoreUtils.getFullPath(fileName));
            LOGGER.info("uploadFileName={}", uploadFileName);
            String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

}
