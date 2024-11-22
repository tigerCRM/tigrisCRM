package com.tiger.crm.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping(value = {"systemBoardList"}, method = RequestMethod.GET)
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
    @RequestMapping(value = {"systemBoard"}, method = RequestMethod.GET)
    public String getSystemBoardPage(@ModelAttribute("systemBoard") SystemBoardDto systemBoard,HttpServletRequest request, HttpServletResponse response, Model model)
    {
        HttpSession session = request.getSession(false);

        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");
        List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
        
        //사용자 정보 가져오기
        model.addAttribute("user", loginUser);
        //회사 옵션 정보 가져오기
        model.addAttribute("companyOptions", companyOptions);

        return "systemBoard";
    }
    
    /*
     * POST 시스템 정보 신규 등록
     * */
    @RequestMapping(value={"systemBoard"}, method = RequestMethod.POST)
    public String postSystemBoard(@ModelAttribute("systemBoard") SystemBoardDto systemBoard, @RequestParam("selectedCompany") int companyId, @RequestParam("companyName") String companyName, HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");

        systemBoard.setCreateId(loginUser.getUserId());

        BoardOpenCompanyDto boardOpenCompanyDto = new BoardOpenCompanyDto();
        boardOpenCompanyDto.setCompanyId(companyId);
        boardOpenCompanyDto.setCompanyName(companyName);

        //정보 저장 로직. 저장 성공시 boardId 리턴받아옴
        int savedBoardId = systemBoardService.insertSystemBoard(systemBoard, boardOpenCompanyDto);

        if(savedBoardId ==0 ){
            LOGGER.info("postSystemBoard ERROR occured!");
            return null;
        }
        //첨부파일 경로에 저장
        List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(systemBoard.getAttachFiles());
        //첨부파일 데이터에 저장
        LOGGER.info("어떻게 나오나???" + uploadFiles.toString());

        for(int i = 0 ; i < uploadFiles.size();i++){
            UploadFileDto uploadFile = new UploadFileDto(uploadFiles.get(i).getOriginFileName(),uploadFiles.get(i).getFileName());
            uploadFile.setFileId("B" + savedBoardId);
            uploadFile.setSeq(i+1);
            uploadFile.setCategory("시스템관리");
            uploadFile.setFilePath(fileDir);

            fileService.insertFile(uploadFile);
            systemBoardService.setSystemBoardFileId(uploadFile.getFileId(),savedBoardId);

        }
        //return null;

        return "redirect:/systemBoardList";
    }
}
