package com.tiger.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.common.file.FileStoreUtils;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.file.UploadFileDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.common.CommonService;

import com.tiger.crm.service.file.FileService;
import com.tiger.crm.service.ticket.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
            model.addAttribute("userClass",userClass);
            // selectbox 바인딩
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
            model.addAttribute("companyOptions", companyOptions);//회사 옵션 정보 가져오기
            model.addAttribute("statusOptions", commonService.getSelectOptions("t_status"));
            model.addAttribute("searchOptions", commonService.getSelectOptions("t_search"));
            // 티켓 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("ticketList", pageResponse);
            return "ticketList";
        } catch (Exception e) {
            throw new CustomException("ticketList : 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 요청관리(티켓관리) 검색
     * 설명 : 요청관리 페이지 검색
     * * 스크립트단에서 ajax로 호출하여 PagingRequest data를 받아서 처리
     * */
    @PostMapping("/ticketList")
    public String searchTickets(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, Model model) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String userClass = String.valueOf(loginUser.getUserClass());
            pagingRequest.setUserClass(userClass);
            // 티켓 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("userClass",userClass);
            model.addAttribute("ticketList", pageResponse);
            // 부분 뷰 렌더링 (리스트 부분만 갱신)
            return "ticketList :: ticketListFragment";
        } catch (Exception e) {
            throw new CustomException("ticketList : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    /*
     * 엑셀다운로드
     * 설명 : 공통 쿼리문 사용하기위해서 Page와 Size값 fix로 사용(이후 변경 필요)
     * */
    @PostMapping("/excelDownload")
    public void excelDownload(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            pagingRequest.setPage(1);
            pagingRequest.setSize(1000000);
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String userClass = String.valueOf(loginUser.getUserClass());
            String companyId = String.valueOf(loginUser.getCompanyId());
            pagingRequest.setUserClass(userClass);
            pagingRequest.setCompanyId(companyId);
            // 데이터 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            List<Map<String, Object>> dataList = pageResponse.getDataList();
            // 공통 Excel 다운로드 서비스 호출
            commonService.downloadExcel(dataList, response, "ticketList");
        } catch (Exception e) {
            throw new CustomException("exceldownload : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    /*
     * 티켓등록 이동
     * 설명 : 신규 티켓 등록시 초기화면 바인딩
     * */
    @GetMapping("/ticketCreate")
    public String goTicketCreatePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
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
            ticketCreate.setStatusCd("OPEN");
            ticketCreate.setTicketId(null);
            //model에 데이터 바인딩
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
            List<TicketDto> ManagerOptions = ticketService.getAllManagerOption();
            model.addAttribute("ManagerOptions", ManagerOptions);
            model.addAttribute("selectedManagerName", managerName);
            model.addAttribute("companyOptions", companyOptions);
            model.addAttribute("selectedCompanyName", companyName);
            model.addAttribute("userClass", userClass); //작성자 레벨
            model.addAttribute("requestTypeCd", commonService.getSelectOptions("t_request"));
            model.addAttribute("supportCd", commonService.getSelectOptions("t_support"));
            model.addAttribute("priorityYn", commonService.getSelectOptions("t_priority"));
            model.addAttribute("statusCd", commonService.getSelectOptions("t_status"));
            model.addAttribute("mode", "write");

            model.addAttribute("ticketCreate", ticketCreate);
        } catch (Exception e) {
            throw new CustomException("ticketCreate : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
        return "ticketCreate";
    }

    /*
     * 티켓 등록(신규)
     * 설명 : 신규 티켓 저장
     * */
    @PostMapping("/ticketCreate")
    public String saveTicketCreate(@ModelAttribute TicketDto ticketDto, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            if( ticketDto.getStatusCd() == null){
                ticketDto.setStatusCd("OPEN");
            }
            if( ticketDto.getMd() == null){
                ticketDto.setMd(BigDecimal.ZERO);
            }
            if (ticketDto.getCompleteDt() == null || ticketDto.getCompleteDt().isEmpty()) {
                ticketDto.setCompleteDt(null);
            }
            // 티켓 저장
            int ticketId = ticketService.saveTicket(ticketDto);
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
            throw new CustomException("ticketCreate : 티켓 저장 중 오류가 발생했습니다.", e);
        }
        return "redirect:ticketList";
    }

    /*
     * 티켓 수정 이동
     * 설명 : 티켓 수정시 티켓 정보 바인딩
     * */
    @PostMapping("/ticketModify")
    public String showticketModifyPage(@ModelAttribute TicketDto ticketDto, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            var id = ticketDto.getTicketId();
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());
            // 티켓 상세 정보 조회 - 로그인한 본인 회사만 볼수있음
            TicketDto ticketDetails = ticketService.getTicketDetails(ticketDto.getTicketId());
            if (!String.valueOf(ticketDetails.getCompanyId()).equals(companyId) && userClass.equals("USER")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 상태 코드 반환
                response.setContentType("text/html; charset=UTF-8");  // 응답 내용 타입과 문자 인코딩 설정
                response.getWriter().write("<script>alert('접근권한이 없습니다.'); window.history.back();</script>");
                response.getWriter().flush();
                return "ticketList";
            }
            //selectbox 데이터 바인딩
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
            List<TicketDto> ManagerOptions = ticketService.getAllManagerOption();
            model.addAttribute("ManagerOptions", ManagerOptions);
            model.addAttribute("selectedManagerName", ticketDetails.getManagerName());
            model.addAttribute("companyOptions", companyOptions);
            model.addAttribute("selectedCompanyName", ticketDetails.getCompanyName());
            model.addAttribute("userClass", ticketDetails.getClass()); //작성자 레벨
            model.addAttribute("requestTypeCd", commonService.getSelectOptions("t_request"));
            model.addAttribute("supportCd", commonService.getSelectOptions("t_support"));
            model.addAttribute("priorityYn", commonService.getSelectOptions("t_priority"));
            model.addAttribute("statusCd", commonService.getSelectOptions("t_status"));
            //첨부파일 정보 가져오기
            List<UploadFileDto> uploadFiles = fileService.getFilesById("ticket",id);
            model.addAttribute("uploadFiles",uploadFiles);
            //댓글 정보 가져오기
            List<CommentDto> commentList = ticketService.getCommentsByTicketId(id);
            model.addAttribute("commentList",commentList);
            model.addAttribute("statusCd", commonService.getSelectOptions("t_status"));
     //       model.addAttribute("DateId",ticketDetails.getExpectedCompleteDt());
            model.addAttribute("mode", "modify");
            model.addAttribute("ticketCreate", ticketDetails);
        } catch (Exception e) {
            throw new CustomException("ticketModify : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }

        return "ticketCreate"; // ticketCreate.html로 이동
    }

    /*
     * 티켓 수정 저장
     * 설명 : 티켓 수정 후 저장
     * */
    @PostMapping("/ticketModifySave")
    public String ticketModifySave(@ModelAttribute TicketDto ticketDto, @RequestParam String deleteSavedAttachFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
        int ticketId = 0 ;
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());
            //로그인한 본인 회사만 수정가능
            if (!String.valueOf(ticketDto.getCompanyId()).equals(companyId) && userClass.equals("USER")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 상태 코드 반환
                response.setContentType("text/html; charset=UTF-8");  // 응답 내용 타입과 문자 인코딩 설정
                response.getWriter().write("<script>alert('접근권한이 없습니다.'); window.history.back();</script>");
                response.getWriter().flush();
                return "ticketList";
            }
            if( ticketDto.getMd() == null){
                ticketDto.setMd(BigDecimal.ZERO);
            }
            if( ticketDto.getCompleteDt().isEmpty()){
                ticketDto.setCompleteDt(null);
            }
            // 티켓 수정 업데이트
            ticketId = ticketService.saveTicketModify(ticketDto);

            if (ticketId == 0) {
                throw new CustomException("티켓 수정에 실패했습니다.");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> deleteFilesList = null;
            // 첨부파일 처리
            try {
                //이전 첨부파일 중 삭제 된 파일들 삭제 작업
               if(!deleteSavedAttachFiles.isEmpty()){
                   String[] files = deleteSavedAttachFiles.split(",");
                    for (String fileName : files) {
                        fileService.deleteFileByFileName(fileName);
                    }
                }

                //신규로 추가된 파일이 있으면 첨부 작업
                LOGGER.info("새로 들어온 파일의 크기 : " + ticketDto.getAttachFiles().size());

                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(ticketDto.getAttachFiles());
                String fileId = fileService.insertFile(uploadFiles, ticketId, "티켓관리");
                ticketService.setTicketFileId(fileId, ticketId);
            } catch (Exception fileException) {
                throw new CustomException("첨부파일 저장 중 오류가 발생했습니다.", fileException);
            }
            //내용수정시 댓글 추가
            var comment = "요청 내용이 수정되었습니다.";
            ticketService.addComment(ticketId, comment, loginUser.getUserId(), ticketDto.getStatusCd());

        } catch (Exception e) {
            throw new CustomException("ticketModifySave : 티켓 수정 중 오류가 발생했습니다.", e);
        }
        return "redirect:/ticketView?id=" + ticketId;

    }

    /*
     * 티켓 상세보기
     * 설명 :
     * */

    @GetMapping("/ticketView")
    public String getTicketsView(@RequestParam(value = "id", required = false) Integer id, @ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {

            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
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
            //첨부파일 정보 가져오기
            List<UploadFileDto> uploadFiles = fileService.getFilesById("ticket",id);
            model.addAttribute("uploadFiles",uploadFiles);
            //댓글 정보 가져오기
            List<CommentDto> commentList = ticketService.getCommentsByTicketId(id);
            model.addAttribute("commentList",commentList);
            model.addAttribute("statusCd", commonService.getSelectOptions("t_status"));
            model.addAttribute("user", loginUser);
            model.addAttribute("ticketinfo", ticketDetails);
            return "ticketView";
        } catch (Exception e) {
            throw new CustomException("ticketView : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    //진행 상태 수정
    @PutMapping("/changeStatus")
    public ResponseEntity<Map<String, String>> updateStepStatus(HttpServletRequest request, @RequestBody Map<String, String> RequestBody) {
        try {

            String newStatus = RequestBody.get("status");
            int id = Integer.parseInt(RequestBody.get("id")); // id는 String으로 전달되므로 변환
            UserLoginDto user = (UserLoginDto) request.getAttribute("user");
            String updateId = user.getUserId();
            String manageId = updateId;
            if ("USER".equals(user.getUserClass())){
                manageId = "";  //사용자가 진행상태 변경한 경우 담당자는 변경되지않음
            }
            //진행상태변경
            ticketService.changeStatus(id, newStatus, manageId);
            //OPEN:등록,RECEIPT:접수,PROGRESS:진행,REVIEW:검토,CLOSED:완료
            String comment = "";
            switch (newStatus) {
                case "OPEN":
                    comment = "티켓 진행상태를 [진행]으로 변경하였습니다.";
                    break;
                case "RECEIPT":
                    comment = "티켓 진행상태를 [접수]로 변경하였습니다.";
                    break;
                case "PROGRESS":
                    comment = "티켓 진행상태를 [진행]으로 변경하였습니다.";
                    break;
                case "REVIEW":
                    comment = "티켓 진행상태를 [검토]로 변경하였습니다.";
                    break;
                case "CLOSED":
                    comment = "티켓 진행상태를 [완료]로 변경하였습니다.";
                    break;
                default:
                    comment = "알 수 없는 상태입니다.";
                    break;
            }

            ticketService.addComment(id, comment, updateId, newStatus);
            // JSON 응답
            Map<String, String> response = new HashMap<>();
            response.put("message", "Step status updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new CustomException("changeStatus : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    @PostMapping("/comments")
    public ResponseEntity<String> addComment(HttpServletRequest request, @RequestBody Map<String, String> RequestBody) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String createId = loginUser.getUserId();
            int ticketId = Integer.parseInt(RequestBody.get("ticketId"));
            String comment = RequestBody.get("comment");
            String statusCd = RequestBody.get("statusCd");
            ticketService.addComment(ticketId, comment, createId, statusCd);

            return ResponseEntity.ok("댓글이 저장되었습니다.");
        } catch (Exception e) {
            throw new CustomException("comments : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<String> getComment(HttpServletRequest request, @RequestBody Map<String, String> RequestBody) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String createId = loginUser.getUserId();
            int ticketId = Integer.parseInt(RequestBody.get("ticketId"));
            String comment = RequestBody.get("comment");
            String statusCd = RequestBody.get("statusCd");
            ticketService.addComment(ticketId, comment, createId, statusCd);

            return ResponseEntity.ok("댓글이 저장되었습니다.");
        } catch (Exception e) {
            throw new CustomException("comments : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    /*
     * 티켓 정보, 댓글,첨부 삭제처리(deleteYn = Y)
     * 추후에 댓글의 첨부까지 삭제처리!!!!!!!!!!
     * */
    @DeleteMapping("/deleteTicket")
    public ResponseEntity<?> deleteTicket(@RequestBody Map<String, Object> data){
        int Id = Integer.parseInt((String) data.get("Id"));
        try{
            ticketService.deleteTicket(Id);             //티켓정보
            ticketService.deleteTicketAnswer(Id);       //댓글정보
            fileService.deleteFiles("ticket",Id);  //첨부파일들
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("삭제되었습니다.");
    }
    /*
     * 댓글 삭제처리(deleteYn = Y)
     * 추후에 댓글의 첨부까지 삭제처리!!!!!!!!!!
     * */
    @DeleteMapping("/deleteCommentId")
    public ResponseEntity<?> deleteCommentId(@RequestBody Map<String, Object> data){
        int Id = Integer.parseInt((String) data.get("Id"));
        try{
            ticketService.deleteTicketAnswerById(Id);       //댓글정보
            //fileService.deleteFiles("ticket",Id);  //첨부파일들
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("삭제되었습니다.");
    }
}
