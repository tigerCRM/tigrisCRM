package com.tiger.crm.controller;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.common.file.FileStoreUtils;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.TicketMapper;
import com.tiger.crm.service.common.CommonService;

import com.tiger.crm.service.file.FileService;
import com.tiger.crm.service.ticket.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FileStoreUtils fileStoreUtils;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileService fileService;

    /*
     * 요청관리(티켓관리)
     * 설명 : 요청관리 페이지 초기화면
     * */
    @GetMapping("/ticketList")
    public String getTickets(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, Model model) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());
            pagingRequest.setUserClass(userClass);
            pagingRequest.setCompanyId(companyId);
            // selectbox 바인딩
            model.addAttribute("statusOptions", commonService.getSelectOptions("t_status"));
            model.addAttribute("searchOptions", commonService.getSelectOptions("t_search"));
            // 티켓 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("ticketList", pageResponse);
            return "ticketList";
        } catch (Exception e) {
            throw new CustomException("데이터를 불러오는 중 오류가 발생했습니다.", e);
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
        } catch (Exception e) {
            throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
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
            throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
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
            String companyName = String.valueOf(loginUser.getCompanyName());
            String userClass = String.valueOf(loginUser.getUserClass());

            Map<String, Object> managerInfo = ticketService.getManagerInfo(companyId);
            String managerId = (String) managerInfo.get("MANAGER_ID");
            String managerName = (String) managerInfo.get("MANAGER_NAME");

            //완료예정일(접수당일 +7일)셋팅
            String expDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            //ticketCreate 객체 바인딩
            TicketDto ticketCreate = new TicketDto();
            ticketCreate.setCreateName(loginUser.getUserName());    //작성자이름
            ticketCreate.setCreateId(loginUser.getUserId());        //작성자ID
            ticketCreate.setCompanyName(loginUser.getCompanyName());  //작성자회사
            ticketCreate.setCompanyId(companyId);                   // 작성자회사ID
            ticketCreate.setManagerId(managerId);               //담당자ID
            ticketCreate.setManagerName(managerName);       //담당자이름
            ticketCreate.setExpectedCompleteDt(expDate);

            //model에 데이터 바인딩
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
            model.addAttribute("companyOptions", companyOptions);
            model.addAttribute("selectedCompanyName", companyName);
            model.addAttribute("userClass", userClass); //작성자 레벨
            model.addAttribute("requestTypeCd", commonService.getSelectOptions("t_request"));
            model.addAttribute("supportCd", commonService.getSelectOptions("t_support"));
            model.addAttribute("priorityYn", commonService.getSelectOptions("t_priority"));

            model.addAttribute("ticketCreate", ticketCreate);
        } catch (Exception e) {
            throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
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
            // 티켓 저장
            ticketDto.setStatusCd("OPEN");
            int ticketId = ticketService.saveTicket(ticketDto, files);
            if (ticketId == 0) {
                throw new CustomException("티켓 저장에 실패했습니다.");
            }
            // 첨부파일 처리
            try {
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(ticketDto.getAttachFiles());
                String fileId = fileService.insertFile(uploadFiles, ticketId, "티켓관리");
                ticketService.setTicketFileId(fileId, ticketId);
            } catch (Exception fileException) {
                throw new CustomException("첨부파일 저장 중 오류가 발생했습니다.", fileException);
            }
        } catch (Exception e) {
            throw new CustomException("티켓 저장 중 오류가 발생했습니다.", e);
        }
        return "redirect:ticketList";
    }
    /*
     * 티켓 상세보기
     * 설명 :
     * */

    @GetMapping("/ticketView")
    public String getTicketsView(@RequestParam(value = "id", required = false) Integer id, @ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HttpSession session = request.getSession(false);
            UserLoginDto loginUser = (UserLoginDto) session.getAttribute("loginUser");

            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());

            // 티켓 상세 정보 조회 - 로그인한 본인 회사만 볼수있음
            TicketDto ticketDetails = ticketService.getTicketDetails(id);
            if (!String.valueOf(ticketDetails.getCompanyId()).equals(companyId) && userClass.equals("USER")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 상태 코드 반환
                response.setContentType("text/html; charset=UTF-8");  // 응답 내용 타입과 문자 인코딩 설정
                response.getWriter().write("<script>alert('접근권한이 없습니다.'); window.history.back();</script>");
                response.getWriter().flush();
                return "ticketList";
            }

            model.addAttribute("statusCd", ticketDetails.getStatusCd());
            model.addAttribute("ticketinfo", ticketDetails);
            return "ticketView";
        } catch (Exception e) {
            throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    //첨부파일 업로드후 파일ID저장
    @PutMapping("/changeStatus")
    public ResponseEntity<Map<String, String>> updateStepStatus(HttpServletRequest request, @RequestBody Map<String, String> RequestBody) {
        try {
            String updateId = "";
            String newStatus = RequestBody.get("status");
            int id = Integer.parseInt(RequestBody.get("id")); // id는 String으로 전달되므로 변환
            UserLoginDto user = (UserLoginDto) request.getAttribute("user");
            if (user != null) {
                updateId = user.getUserId(); // getUserId() 메서드를 통해 userId 추출
                System.out.println("update ID: " + updateId);
            } else {
                System.out.println("User not found");
            }

            // 서비스 호출
            ticketService.changeStatus(id, newStatus, updateId);
            // JSON 응답
            Map<String, String> response = new HashMap<>();
            response.put("message", "Step status updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }
}
