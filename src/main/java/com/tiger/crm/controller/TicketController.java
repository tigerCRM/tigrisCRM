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

import com.tiger.crm.repository.dto.ticket.StatusMapper;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
    @Autowired
    private StatusMapper statusMapper;
    /*
     * 요청관리(요청관리)
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
            pagingRequest.setCreateId("");
            pagingRequest.setUserId(loginUser.getUserId());
            // 요청 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("ticketList", pageResponse);
            return "ticketList";
        } catch (Exception e) {
            throw new CustomException("ticketList : 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 요청관리(요청관리) 검색
     * 설명 : 요청관리 페이지 검색
     * * 스크립트단에서 ajax로 호출하여 PagingRequest data를 받아서 처리
     * */
    @PostMapping("/ticketList")
    public String searchTickets(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, Model model) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String userClass = String.valueOf(loginUser.getUserClass());
            String userid = String.valueOf(loginUser.getUserId());
            String companyId = String.valueOf(loginUser.getCompanyId());
            pagingRequest.setUserClass(userClass);
            if (pagingRequest.getCreateId().equals("true")){
                pagingRequest.setCreateId(userid);
            }else{
                pagingRequest.setCreateId("");
            }
            pagingRequest.setCompanyId(companyId);
            pagingRequest.setUserId(loginUser.getUserId());
            // 요청 조회
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
     * 요청관리(요청관리)
     * 설명 : 요청관리 페이지 초기화면
     * */
    @GetMapping("/ticketListAdmin")
    public String getTicketsAdmin(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, Model model) {
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
            pagingRequest.setCreateId("");
            pagingRequest.setUserId(loginUser.getUserId());
            // 요청 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("ticketList", pageResponse);
            return "ticketListAdmin";
        } catch (Exception e) {
            throw new CustomException("ticketList : 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    @PostMapping("/ticketListAdmin")
    public String searchTicketsAdmin(@ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, Model model) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String userClass = String.valueOf(loginUser.getUserClass());
            String userid = String.valueOf(loginUser.getUserId());
            String companyId = String.valueOf(loginUser.getCompanyId());
            pagingRequest.setUserClass(userClass);
            if (pagingRequest.getCreateId().equals("true")){
                pagingRequest.setCreateId(userid);
            }else{
                pagingRequest.setCreateId("");
            }
            pagingRequest.setCompanyId(companyId);
            pagingRequest.setUserId(loginUser.getUserId());
            // 요청 조회
            PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
            model.addAttribute("userClass",userClass);
            model.addAttribute("ticketList", pageResponse);
            // 부분 뷰 렌더링 (리스트 부분만 갱신)
            return "ticketListAdmin :: ticketListFragment";
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
            String userid = String.valueOf(loginUser.getUserId());
            pagingRequest.setUserClass(userClass);
            pagingRequest.setCompanyId(companyId);
            if (pagingRequest.getCreateId().equals("true")){
                pagingRequest.setCreateId(userid);
            }else{
                pagingRequest.setCreateId("");
            }
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
     * 요청등록 이동
     * 설명 : 신규 요청 등록시 초기화면 바인딩
     * */
    @GetMapping("/ticketCreate")
    public String goTicketCreatePage(@RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpServletResponse response, Model model) {
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
            ticketCreate.setCreateDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if (id!=null){  //연관요청 요청일경우
                // 요청 상세 정보 조회 - 로그인한 본인 회사만 볼수있음
                TicketDto parentTicketDetails = ticketService.getTicketDetails(id);
                if (!String.valueOf(parentTicketDetails.getCompanyId()).equals(companyId) && userClass.equals("USER")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 상태 코드 반환
                    response.setContentType("text/html; charset=UTF-8");  // 응답 내용 타입과 문자 인코딩 설정
                    response.getWriter().write("<script>alert('접근권한이 없습니다.'); window.history.back();</script>");
                    response.getWriter().flush();
                    return "ticketList";
                }
                ticketCreate.setParentTicketCd(id);
                ticketCreate.setTitle("["+String.valueOf(id)+"] Re:"+ parentTicketDetails.getTitle());
                ticketCreate.setContent("<br>"+System.lineSeparator()+System.lineSeparator()+"===========================연관요청==========================="+System.lineSeparator()
                        + "["+String.valueOf(id)+"] Re:"+ parentTicketDetails.getTitle()
                        + System.lineSeparator()  +parentTicketDetails.getContent());
            }
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
     * 요청 등록(신규)
     * 설명 : 신규 요청 저장
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
            if (ticketDto.getCompleteDt() == null || ticketDto.getCompleteDt().isEmpty() ) {
                ticketDto.setCompleteDt(null);
            }
            if (ticketDto.getTitle().isEmpty()) {
               // throw new CustomException("요청 저장에 실패했습니다.");
            }
            // 요청 저장
            int ticketId = ticketService.saveTicket(ticketDto);
            if (ticketId == 0) {
                throw new CustomException("요청 저장에 실패했습니다.");
            }
            // 첨부파일 처리
            try {
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(ticketDto.getAttachFiles(),"T");
                String fileId = fileService.insertFile(uploadFiles, ticketId, "요청관리");
                ticketService.setTicketFileId(fileId, ticketId);
            } catch (Exception fileException) {
                throw new CustomException("첨부파일 저장 중 오류가 발생했습니다.", fileException);
            }
        } catch (Exception e) {
            throw new CustomException("ticketCreate : 요청 저장 중 오류가 발생했습니다.", e);
        }
        return "redirect:ticketList";
    }

    /*
     * 요청 수정 이동
     * 설명 : 요청 수정시 요청 정보 바인딩
     * */
    @PostMapping("/ticketModify")
    public String showticketModifyPage(@ModelAttribute TicketDto ticketDto, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            var id = ticketDto.getTicketId();
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());
            // 요청 상세 정보 조회 - 로그인한 본인 회사만 볼수있음
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
            model.addAttribute("userClass", userClass); //작성자 레벨
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
            model.addAttribute("mode", "modify");
            model.addAttribute("ticketCreate", ticketDetails);
        } catch (Exception e) {
            throw new CustomException("ticketModify : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }

        return "ticketCreate"; // ticketCreate.html로 이동
    }

    /*
     * 요청 수정 저장
     * 설명 : 요청 수정 후 저장
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
            if (ticketDto.getCompleteDt() == null || ticketDto.getCompleteDt().isEmpty()) {
                ticketDto.setCompleteDt(null);
            }else{
                LocalDateTime getdate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = getdate.format(formatter);
                ticketDto.setCompleteDt(formattedDate);
            }
            //사용자는 무조건 등록일 경우에만 수정가능 함
            if (userClass.equals("USER")){
                ticketDto.setStatusCd("OPEN");
            }
            // 요청 수정 업데이트
            ticketId = ticketService.saveTicketModify(ticketDto);

            if (ticketId == 0) {
                throw new CustomException("요청 수정에 실패했습니다.");
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

                if (ticketDto.getAttachFiles() != null && !ticketDto.getAttachFiles().isEmpty()) {
                    List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(ticketDto.getAttachFiles(),"T");
                    String fileId = fileService.insertFile(uploadFiles, ticketId, "요청관리");
                    ticketService.setTicketFileId(fileId, ticketId);
                }
            } catch (Exception fileException) {
                throw new CustomException("첨부파일 저장 중 오류가 발생했습니다.", fileException);
            }
            //내용수정시 댓글 추가
            String statusText =  StatusMapper.getStatusText(ticketDto.getStatusCd());
            // comment에 변환된 값 넣기
            var comment = "[" + statusText + "] 요청 내용이 수정되었습니다.";
            CommentDto commentDto = new CommentDto();
            commentDto.setTicketId(ticketId);
            commentDto.setContent(comment);
            commentDto.setCreateId(loginUser.getUserId());
            commentDto.setStatusCd(ticketDto.getStatusCd());
            ticketService.addComment(commentDto);

        } catch (Exception e) {
            LOGGER.error("요청 수정 중 오류가 발생했습니다.", e);
            throw new CustomException("ticketModifySave : 요청 수정 중 오류가 발생했습니다.", e);
        }
        return "redirect:/ticketView?id=" + ticketId;

    }

    /*
     * 요청 상세보기
     * 설명 :
     * */

    @GetMapping("/ticketView")
    public String getTicketsView(@RequestParam(value = "id", required = false) Integer id, @ModelAttribute PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {

            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String companyId = String.valueOf(loginUser.getCompanyId());
            String userClass = String.valueOf(loginUser.getUserClass());
            // 요청 상세 정보 조회 - 로그인한 본인 회사만 볼수있음
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
            //댓글 정보 가져오기 + 댓글에 대한 첨부파일
          //  List<CommentDto> commentList = ticketService.getCommentsByTicketId(id);
            List<CommentDto> commentList = getCommentsWithFiles(id);
            model.addAttribute("commentList",commentList);
            model.addAttribute("statusCd", commonService.getSelectOptions("t_status"));
            model.addAttribute("user", loginUser);
            model.addAttribute("userClass", userClass); //작성자 레벨
            model.addAttribute("ticketinfo", ticketDetails);
            return "ticketView";
        } catch (Exception e) {
            throw new CustomException("ticketView : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    //댓글 정보 가져오기 + 댓글에 대한 첨부파일
    public List<CommentDto> getCommentsWithFiles(int ticketId) {
        // 댓글 목록 가져오기
        List<CommentDto> commentList = ticketService.getCommentsByTicketId(ticketId);
        // 각 댓글에 대해 첨부파일 리스트 추가
        for (CommentDto comment : commentList) {
            List<UploadFileDto> commentFileList = fileService.getFilesById("comment", comment.getAnswerId());
            comment.setAttachFiles(commentFileList); // 첨부파일 추가
        }
        return commentList;
    }

    //진행 상태 수정
    @PutMapping("/changeStatus")
    public ResponseEntity<Map<String, String>> updateStepStatus(HttpServletRequest request, @RequestBody Map<String, String> RequestBody,CommentDto commentDto) {
        try {

            String newStatus = RequestBody.get("status");
            int id = Integer.parseInt(RequestBody.get("id")); // id는 String으로 전달되므로 변환
            UserLoginDto user = (UserLoginDto) request.getAttribute("user");
            String updateId = user.getUserId();
            String manageId = updateId;
            if ("USER".equals(user.getUserClass())){
                manageId = "";  //사용자가 진행상태 변경한 경우 담당자는 변경되지않음
            }
            String md = RequestBody.get("md");
            //진행상태변경
            ticketService.changeStatus(id, newStatus, manageId,md);
            //OPEN:등록,RECEIPT:접수,PROGRESS:진행,REVIEW:검토,CLOSED:완료
            String comment = "";
            switch (newStatus) {
                case "OPEN":
                    comment = "요청 진행상태를 [진행]으로 변경하였습니다.";
                    break;
                case "RECEIPT":
                    comment = "요청 진행상태를 [접수]로 변경하였습니다.";
                    break;
                case "PROGRESS":
                    comment = "요청 진행상태를 [진행]으로 변경하였습니다.";
                    break;
                case "REVIEW":
                    comment = "요청 진행상태를 [검토]로 변경하였습니다.";
                    break;
                case "CLOSED":
                    comment = "요청 진행상태를 [완료]로 변경하였습니다.";
                    break;
                default:
                    comment = "알 수 없는 상태입니다.";
                    break;
            }

            commentDto.setTicketId(id);
            commentDto.setContent(comment);
            commentDto.setCreateId(updateId);
            commentDto.setStatusCd(newStatus);
            commentDto.setAlarmYN("N");
            ticketService.addComment(commentDto);
            // JSON 응답
            Map<String, String> response = new HashMap<>();
            response.put("message", "Step status updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new CustomException("changeStatus : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    @PostMapping("/comments")
    public ResponseEntity<Map<String, Object>> addComment(
            HttpServletRequest request, @RequestPart("data") CommentDto commentDto,@RequestPart(value = "file", required = false) List<MultipartFile> files) {

        try {
            // 로그인 사용자 정보 가져오기
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String createId = loginUser.getUserId();
            commentDto.setCreateId(createId);
            // 댓글 저장
            int commentId = ticketService.addComment(commentDto);

            // 파일 저장 처리
            if (files != null) {
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(files,"C");
                String fileId = fileService.insertFile(uploadFiles, commentId, "댓글");
                // 댓글 첨부파일 테이블에 저장
                ticketService.setTicketFileId(fileId, commentId);
            }

            // 성공 응답
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "댓글이 저장되었습니다.");
            return ResponseEntity.ok(response);

        }  catch (Exception e) {
            throw new CustomException("댓글 등록 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

   /* @GetMapping("/comments")
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
    }*/

    /*
     * 요청 정보, 댓글,첨부 삭제처리(deleteYn = Y)
     * 추후에 댓글의 첨부까지 삭제처리!!!!!!!!!!
     * */
    @DeleteMapping("/deleteTicket")
    public ResponseEntity<?> deleteTicket(@RequestBody Map<String, Object> data){
        int Id = Integer.parseInt((String) data.get("Id"));
        try{
            ticketService.deleteTicket(Id);             //요청정보
            ticketService.deleteTicketAnswer(Id);       //댓글정보
            ticketService.deleteTicketAnswerFile(Id);    //댓글첨부파일등
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
            ticketService.deleteTicketAnswerFileById(Id);    //댓글첨부파일등
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("삭제되었습니다.");
    }


    /*
     * 댓글 첨부파일
     * 설명 : 저장
     * */
    @PostMapping("/addAttachComment")
    public ResponseEntity<String> addAttachComment(HttpServletRequest request, @RequestBody Map<String, String> RequestBody) {
        try {
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            String createId = loginUser.getUserId();
            int commnetId = Integer.parseInt(RequestBody.get("commnetId"));
            // 첨부파일 처리
            try {
                List<MultipartFile> fileDataList = new ObjectMapper().readValue(RequestBody.get("file"), new TypeReference<>() {});
                List<UploadFileDto> uploadFiles = fileStoreUtils.storeFiles(fileDataList,"C");
                String fileId = fileService.insertFile(uploadFiles, commnetId, "댓글");
                //댓글 첨부파일 테이블에 저장
                //  ticketService.setTicketFileId(fileId, ticketId);
            } catch (Exception fileException) {
                throw new CustomException("첨부파일 저장 중 오류가 발생했습니다.", fileException);
            }
        } catch (Exception e) {
            throw new CustomException("comments : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
        return ResponseEntity.ok("업로드되었습니다.");
    }
    //진행 상태 수정
    @PostMapping("/chSatisfaction")
    public ResponseEntity<Map<String, String>> chSatisfaction(HttpServletRequest request, @RequestBody Map<String, String> RequestBody,CommentDto commentDto) {
        try {

            String newStatus = RequestBody.get("status");
            String result = RequestBody.get("result");        //만족도 점수
            String sComment = RequestBody.get("comment");      //만족도의견
            int id = Integer.parseInt(RequestBody.get("id")); // id는 String으로 전달되므로 변환
            UserLoginDto user = (UserLoginDto) request.getAttribute("user");
            String updateId = user.getUserId();
            //진행상태변경
            ticketService.chSatisfaction(id, newStatus, result, sComment, updateId);
            String comment = "요청 진행상태를 [완료]로 변경하였습니다.\n";
            String score = "";
            switch (result) {
                case "1": score = "매우불만"; break;
                case "2": score = "불만"; break;
                case "3": score = "보통"; break;
                case "4": score = "만족"; break;
                case "5": score = "매우만족"; break;
                default:
                    score = ""; break;
            }
            comment = comment+ "[" +score +"]"+ sComment;
            commentDto.setTicketId(id);
            commentDto.setContent(comment);
            commentDto.setCreateId(user.getUserId());
            commentDto.setStatusCd(newStatus);
            ticketService.addComment(commentDto);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Step status updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new CustomException("changeStatus : 예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

}
