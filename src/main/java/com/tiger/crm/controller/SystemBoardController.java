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

        return "redirect:/systemBoardList";
    }
}
