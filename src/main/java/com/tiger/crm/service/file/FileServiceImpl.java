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

    /*
    * 첨부파일 리스트 가져오기
    * 작성자 : 제예솔
    * 설명 : type은 첨부한 경로의 타입(board = 게시판, ticket = 티켓) , id (게시판 또는 티켓의 아이디)
    * 이외의 첨부파일 로드 경로가 있을 시 else if 붙여서 로직 추가하시면 됩니다
    * */
    @Override
    public List<UploadFileDto> getFilesById(String type, int id){
        String fileId;
        List<UploadFileDto> files;
        if("board".equals(type)){ // 게시판 첨부파일
            fileId = "B".concat(String.valueOf(id));
        }else if("ticket".equals(type)){ // 티켓 첨부파일
            fileId = "T".concat(String.valueOf(id));
        }else{
            LOGGER.info("파일 찾기 오류 : 명시 되지 않은 요청 타입");
            return null;
        }
        files = fileMapper.getFiles(fileId);
        return files;
    }
    /*
    * 첨부파일 이름으로 첨부파일 정보 가져오기
    * */
    @Override
    public UploadFileDto getFileByFileName(String fileName){
        return fileMapper.getFileByFileName(fileName);
    };
    @Override
    public void deleteFiles(String type, int id){
        String fileId = null;
        if("board".equals(type)){ // 게시판 첨부파일
            fileId = "B".concat(String.valueOf(id));
        }else if("ticket".equals(type)){ // 티켓 첨부파일
            fileId = "T".concat(String.valueOf(id));
        }else{
            LOGGER.info("파일 찾기 오류 : 명시 되지 않은 요청 타입");
        }
        fileMapper.deleteFilesByFileId(fileId);

    }
    @Override
    public void deleteFileByFileName(String fileName){
        fileMapper.deleteFileByFileName(fileName);
    };
}
