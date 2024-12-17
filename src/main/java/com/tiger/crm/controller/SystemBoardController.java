package com.tiger.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.file.FileStoreUtils;
import com.tiger.crm.common.validation.SystemBoardValidator;
import com.tiger.crm.common.validation.UserLoginValidator;
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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SystemBoardController {
    
    /*
    * 시스템관리
    * 작성자 : 제예솔
    * 설명 : 시스템관리 리스트, 글작성, 수정 및 삭제
    * */
    private final ConfigProperties config;
    private final CommonService commonService;
    private final SystemBoardService systemBoardService;
    private final FileService fileService;
    private final FileStoreUtils fileStoreUtils;

    private final MessageSource messageSource;

    private final SystemBoardValidator systemBoardValidator;

    //application.yml 에 파일 위치 명시되어 있음
    @Value("${file.dir}")
    private String fileDir;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @InitBinder("systemBoard")
    public void init(WebDataBinder dataBinder) {
        LOGGER.info("init binder {}", dataBinder);
        dataBinder.addValidators(systemBoardValidator);
    }

    /*
    * GET 시스템 정보 리스트 페이지
    * */
    @GetMapping("/systemBoardList")
    public String getSystemBoardListPage(PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        model.addAttribute("searchOptions", commonService.getSelectOptions("b_search"));
        List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
        model.addAttribute("companyOptions", companyOptions);
        PagingResponse<Map<String, Object>> pageResponse = systemBoardService.getSystemBoardList(pagingRequest);
        model.addAttribute("systemBoardList", pageResponse);
        return "systemBoardList";
    }

    /*
     * 시스템 정보 리스트 검색
     * 설명 : 요청관리 페이지 검색
     * * 스크립트단에서 ajax로 호출하여 PagingRequest data를 받아서 처리
     * */
    @PostMapping("/systemBoardList")
    public String searchSystemBoardListPage(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {
            // 티켓 조회
            PagingResponse<Map<String, Object>> pageResponse = systemBoardService.getSystemBoardList(pagingRequest);
            model.addAttribute("systemBoardList", pageResponse);
            // 부분 뷰 렌더링 (리스트 부분만 갱신)
            return "systemBoardList :: systemBoardListFragment";
        } catch (IllegalArgumentException e) {
            // 입력 값에 대한 오류 처리 (예: 유효하지 않은 파라미터)
            model.addAttribute("error", "잘못된 입력 값이 있습니다. 다시 확인해 주세요.");
            e.printStackTrace();
        } catch (Exception e) {
            // 데이터 조회 중 발생한 일반적인 오류 처리
            model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
            e.printStackTrace();
        }

        // 오류 발생 시 전체 페이지로 돌아가도록 처리
        return "systemBoardList";  // 기본 화면으로 이동
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
    public String postSystemBoard(@Validated @ModelAttribute("systemBoard") SystemBoardDto systemBoard,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  Model model){

        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");

        //최초 인입된 dto 에 대해 validation 수행 후 반환
        if (bindingResult.hasErrors()) {
            LOGGER.info("validation error 발생={}",bindingResult);
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
            model.addAttribute("mode", "write");//글작성
            model.addAttribute("companyOptions", companyOptions);
            return "systemBoard";
        }

        //작성자 저장
        systemBoard.setCreateId(loginUser.getUserId());
        
        //정보 저장 로직. Board 와 OpenCompany DB 저장 후 BoardId 리턴받음
        int savedBoardId = systemBoardService.insertSystemBoard(systemBoard, new BoardOpenCompanyDto(Integer.parseInt(systemBoard.getCompanyId()),systemBoard.getCompanyName()));

        if(savedBoardId ==0 ){
            LOGGER.info("postSystemBoard ERROR occured!");
            return null;
        }
        
        //첨부파일
        try{
            if(!systemBoard.getAttachFiles().get(0).isEmpty()){
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(systemBoard.getAttachFiles()); // 경로에 저장
                String fileId = fileService.insertFile(uploadFiles, savedBoardId, "시스템관리"); //DB 에 저장
                systemBoardService.setSystemBoardFileId(fileId,savedBoardId);//DB에 저장
            }
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
            @Validated @ModelAttribute("systemBoard") SystemBoardDto systemBoard,
            BindingResult bindingResult,
            @RequestParam String deleteSavedAttachFiles,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model){

        //최초 인입된 dto 에 대해 validation 수행 후 반환
        if (bindingResult.hasErrors()) {
            LOGGER.info("validation error 발생={}",bindingResult);

            List<String> errorMessages = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String errorMessage = messageSource.getMessage(error.getCode(), null, null);
                errorMessages.add(errorMessage);
            }

            // HTTP 400 Bad Request와 오류 메시지 반환
            return ResponseEntity.badRequest().body(Map.of("errors", errorMessages));
        }

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
            if(!systemBoard.getAttachFiles().get(0).isEmpty()){
                LOGGER.info("새로 들어온 파일의 크기 : " + systemBoard.getAttachFiles().size());
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(systemBoard.getAttachFiles()); // 경로에 저장
                String fileId = fileService.insertFile(uploadFiles, systemBoard.getBoardId(), "시스템관리"); //DB 에 저장
                systemBoardService.setSystemBoardFileId(fileId,systemBoard.getBoardId());//DB에 저장
            }

        }catch (Exception e){
            LOGGER.info("게시물 수정 오류 발생 : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("수정이 완료되었습니다");
    }

}
