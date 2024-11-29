package com.tiger.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.file.FileStoreUtils;
import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.board.SystemBoardService;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.file.FileService;
import com.tiger.crm.service.ticket.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
public class SystemBoardController {
    
    /*
    * 시스템관리
    * 작성자 : 제예솔
    * 설명 : 시스템관리 리스트, 글작성, 수정 및 삭제
    * */
    @Autowired
    private ConfigProperties config;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SystemBoardService systemBoardService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileStoreUtils fileStoreUtils;

    //application.yml 에 파일 위치 명시되어 있음
    @Value("${file.dir}")
    private String fileDir;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    /*
    * GET 시스템 정보 리스트 페이지
    * */
    @GetMapping("/systemBoardList")
    public String getSystemBoardListPage(SystemBoardDto systemBoardDto, PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        model.addAttribute("searchOptions", commonService.getSelectOptions("t_search"));
        PagingResponse<Map<String, Object>> pageResponse = systemBoardService.getSystemBoardList(pagingRequest);
        model.addAttribute("systemBoardList", pageResponse);
        return "systemBoardList";
    }
    
    /*
     * GET 시스템 정보 입력창 페이지
     * */
    @GetMapping("/systemBoard")
    public String getSystemBoardPage(
            @RequestParam(value = "boardId", required = false) Integer boardId,
            @RequestParam(value = "mode", required = false) String mode,
            @ModelAttribute("systemBoard") SystemBoardDto systemBoard,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");
        List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
        if(boardId != null ){ //글 불러오기 로직
            SystemBoardDto loadSystemBoard = systemBoardService.getSystemBoardByBoardId(boardId);

            List<UploadFileDto> uploadFiles = fileService.getFilesById("board",boardId);

            LOGGER.info("test : " + uploadFiles.toString());

            model.addAttribute("user", loginUser);//사용자 정보 가져오기
            model.addAttribute("companyOptions", companyOptions);//회사 옵션 정보 가져오기
            model.addAttribute("systemBoard",loadSystemBoard);//시스템 정보 가져오기
            model.addAttribute("uploadFiles",uploadFiles);//첨부파일 정보 가져오기

            if("modify".equals(mode)){
                model.addAttribute("mode", "modify"); // 글 수정
            }else{
                model.addAttribute("mode", "read"); // 글 상세
            }

        }else{//신규 글 작성 로직

            model.addAttribute("user", loginUser);//사용자 정보 가져오기
            model.addAttribute("companyOptions", companyOptions);//회사 옵션 정보 가져오기
            model.addAttribute("mode", "write");//글작성
            model.addAttribute("uploadFiles",null);
        }

        return "systemBoard";
    }
    
    /*
     * POST 시스템 정보 신규 등록
     * */
    @PostMapping("/systemBoard")
    public String postSystemBoard(@ModelAttribute("systemBoard") SystemBoardDto systemBoard,
                                  @RequestParam("selectedCompany") int companyId,
                                  @RequestParam("companyName") String companyName,
                                  HttpServletRequest request,
                                  HttpServletResponse response){

        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");
        
        //작성자 저장
        systemBoard.setCreateId(loginUser.getUserId());
        
        //정보 저장 로직. Board 와 OpenCompany DB 저장 후 BoardId 리턴받음
        int savedBoardId = systemBoardService.insertSystemBoard(systemBoard, new BoardOpenCompanyDto(companyId,companyName));

        if(savedBoardId ==0 ){
            LOGGER.info("postSystemBoard ERROR occured!");
            return null;
        }
        
        //첨부파일
        try{
            List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(systemBoard.getAttachFiles()); // 경로에 저장
            fileService.insertFile(uploadFiles, savedBoardId); //DB 에 저장
            
        }catch (Exception e){
            LOGGER.info(e.toString());
            return null;
        }

        return "redirect:systemBoardList";
    }

    /*
     * DELETE 시스템 정보 삭제
     * */
    @DeleteMapping("/systemBoard")
    public ResponseEntity<?> deleteSystemBoard(@RequestBody Map<String, Object> data){
        int boardId = Integer.parseInt((String) data.get("boardId"));

        try{
            systemBoardService.deleteSystemBoardByBoardId(boardId); //t_board 안의 정보 삭제, t_board_open_company 정보 삭제(deleteYn = Y)
            fileService.deleteFiles("board",boardId); //t_board 에 물려있는 첨부파일들 삭제(deleteYn = y)
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("삭제되었습니다.");
    }

    /*
     * PUT 시스템 정보 수정저장
     * */
    @PutMapping("/systemBoard")
    public ResponseEntity<?> updateSystemBoard(
            @ModelAttribute("systemBoard") SystemBoardDto systemBoard,
            @RequestParam String deleteSavedAttachFiles,
            HttpServletRequest request,
            HttpServletResponse response){

        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");
        systemBoard.setUpdateId(loginUser.getUserId());

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deleteFilesList = null;

        try{
            //게시글에 대한 작업
            systemBoardService.updateSystemBoard(systemBoard);

            //이전 첨부파일 중 삭제 된 파일들 삭제 작업
            if(!deleteSavedAttachFiles.isEmpty()){
                deleteFilesList = objectMapper.readValue(deleteSavedAttachFiles, new TypeReference<List<String>>() {});
                for (String fileName : deleteFilesList) {
                    fileService.deleteFileByFileName(fileName);
                }
            }
            //신규로 추가된 파일이 있으면 첨부 작업
            //첨부파일?
            try{
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(systemBoard.getAttachFiles()); // 경로에 저장
                fileService.insertFile(uploadFiles, systemBoard.getBoardId()); //DB 에 저장

            }catch (Exception e){
                LOGGER.info(e.toString());
                return null;
            }

        }catch (Exception e){
            LOGGER.info("뭔가가 잘못된듯...");
        }

        return null;
    }

}
