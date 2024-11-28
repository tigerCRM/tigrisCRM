package com.tiger.crm.controller;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.TicketMapper;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.ticket.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CommonService commonService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /*
     * 요청관리(티켓관리)
     * 설명 : 요청관리 페이지 초기화면
     * */
    @GetMapping("/ticketList")
    public String getTickets(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, Model model) {
        try {
            HttpSession session = request.getSession(false);
            UserLoginDto loginUser = (UserLoginDto) session.getAttribute("loginUser");
            LOGGER.info("세션정보 : " + session.toString());
            if (session == null) {
                LOGGER.info("세션 없음");
                return "redirect:login";
            }
            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());
            pagingRequest.setUserClass(userClass);
            pagingRequest.setCompanyId(companyId);
            model.addAttribute("user", loginUser);

            // selectbox 바인딩
            model.addAttribute("statusOptions", commonService.getSelectOptions("t_status"));
            model.addAttribute("searchOptions", commonService.getSelectOptions("t_search"));
            // 티켓 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("ticketList", pageResponse);
            return "ticketList";
        } catch (Exception e) {
            // 오류 로그 기록
            e.printStackTrace();
            model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "errorPage";  // 에러 페이지로 이동 (필요 시 별도의 오류 페이지 생성)
        }
    }

    /*
     * 요청관리(티켓관리) 검색
     * 설명 : 요청관리 페이지 검색
     * * 스크립트단에서 ajax로 호출하여 PagingRequest data를 받아서 처리
     * */
    @PostMapping("/ticketList")
    public String searchTickets(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {
            // 티켓 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("ticketList", pageResponse);
            // 부분 뷰 렌더링 (리스트 부분만 갱신)
            return "ticketList :: ticketListFragment";
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
        return "ticketList";  // 기본 화면으로 이동
    }

    /*
     * 엑셀다운로드
     * 설명 : 공통 쿼리문 사용하기위해서 Page와 Size값 fix로 사용(이후 변경 필요)
     * */
    @PostMapping("/exceldownload")
    public void excelDownload(@ModelAttribute PagingRequest pagingRequest, HttpServletResponse response) {
        try {
            pagingRequest.setPage(1);
            pagingRequest.setSize(1000000);
            // 데이터 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            List<Map<String, Object>> dataList = pageResponse.getDataList();
            // 공통 Excel 다운로드 서비스 호출
            commonService.downloadExcel(dataList, response, "ticketList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * 티켓등록 이동
     * 설명 : 신규 티켓 등록시 초기화면 바인딩
     * */
    @GetMapping("/ticketCreate")
    public String goTicketCreatePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HttpSession session = request.getSession(false);
            UserLoginDto loginUser = (UserLoginDto) session.getAttribute("loginUser");
            String companyId = String.valueOf(loginUser.getCompanyId());

            LOGGER.info("세션정보 : " + session.toString());
            if (session == null) {
                LOGGER.info("세션 없음");
                return "redirect:login";
            }
            model.addAttribute("user", loginUser);

            Map<String, Object> managerInfo = ticketService.getManagerInfo(companyId);
            String managerId = (String) managerInfo.get("MANAGER_ID");
            String managerName = (String) managerInfo.get("MANAGER_NAME");

            //완료예정일(접수당일 +7일)셋팅
            String expDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            TicketDto ticketCreate = new TicketDto();
            ticketCreate.setCreateName(loginUser.getUserName());    //작성자이름
            ticketCreate.setCreateId(loginUser.getUserId());        //작성자ID
            ticketCreate.setCompanyName(loginUser.getCompanyName());  //작성자회사
            ticketCreate.setCompanyId(companyId);                   // 작성자회사ID
            ticketCreate.setManagerId(managerId);               //담당자ID
            ticketCreate.setManagerName(managerName);       //담당자이름
            ticketCreate.setExpectedCompleteDt(expDate);

            // selectbox 바인딩
            model.addAttribute("requestTypeCd", commonService.getSelectOptions("t_request"));
            model.addAttribute("supportCd", commonService.getSelectOptions("t_support"));
            model.addAttribute("priorityYn", commonService.getSelectOptions("t_priority"));

            model.addAttribute("ticketCreate", ticketCreate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ticketCreate";
    }
    /*
     * 티켓 등록(신규)
     * 설명 : 신규 티켓 등록시 초기화면 바인딩
     * */
    @PostMapping("/ticketCreate")
    public String saveTicketCreate(@ModelAttribute TicketDto ticketDto, @RequestParam("attachFiles") List<MultipartFile> files, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            ticketDto.setStatusCd("OPEN");
            boolean result = ticketService.saveTicket(ticketDto, files);
            if (!result) {
                LOGGER.error("Failed to save ticket");
                model.addAttribute("error", "티켓 저장에 실패했습니다.");
                return "ticketCreate";
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving ticket", e);
            model.addAttribute("errorMessage", "티켓 저장 중 오류가 발생했습니다.");
            return "common/error"; // error 페이지로 이동
        }
        return "redirect:ticketList";
    }
}
