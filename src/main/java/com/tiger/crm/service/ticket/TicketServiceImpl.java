package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.alert.AlertType;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.repository.mapper.TicketMapper;
import com.tiger.crm.service.alert.AlertService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private AlertService alertService;
    @Autowired
    private MailService mailService;
    @Value("${app.base-url}")
    private String baseUrl;


    // 전체 요청 수 조회 (null 체크 포함)
    @Override
    public PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest) {

        List<Map<String, Object>> ticketList = ticketMapper.getTicketList(pagingRequest);

        int totalRecords = getTicketListCount(pagingRequest);

        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }

    // 전체 요청 수 조회 (null 체크 포함)
    @Override
    public PagingResponse<Map<String, Object>> getTicketListAdmin(PagingRequest pagingRequest) {

        List<Map<String, Object>> ticketList = ticketMapper.getTicketListAdmin(pagingRequest);

        int totalRecords = getTicketListAdminCount(pagingRequest);

        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }

    // 전체 요청 수를 조회하는 메서드
    private int getTicketListCount(PagingRequest pagingRequest) {
        Integer count = ticketMapper.getTicketListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }
    // 전체 요청 수를 조회하는 메서드(관리자)
    private int getTicketListAdminCount(PagingRequest pagingRequest) {
        Integer count = ticketMapper.getTicketListAdminCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }


    @Override
    public PagingResponse<Map<String, Object>> getTicketListAnalytics(PagingRequest pagingRequest) {

        List<Map<String, Object>> ticketList = ticketMapper.getTicketListAnalytics(pagingRequest);

        int totalRecords = 0;

        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }

    //요청저장
    public int saveTicket(TicketDto ticketDto) throws MessagingException {
        int resultCount =  ticketMapper.insertTicketInfo(ticketDto);
        if(resultCount != 1){
            return 0;
        }

        // 고유번호가 TicketDto에 저장됨
        int ticketId = ticketDto.getTicketId();

        // [알림, 메일] 발송 파트
        /*if(resultCount > 0){
            Map<String, Object> model = new HashMap<>();
            model.put("userName", ticketDto.getManagerName());
            model.put("ticketTitle", ticketDto.getTitle());
            model.put("ticketStatus", ticketDto.getStatusCd());
            model.put("ticketUrl", baseUrl + "/ticketView?id=" + ticketDto.getTicketId() + "&redirect=/ticketView?id=" + ticketDto.getTicketId());
            mailService.sendEmail(ticketDto.getManagerId(),"요청등록", "ticket-email", model);
            alertService.sendAlert(AlertType.TICKET_STATUS, ticketDto.getStatusCd(), ticketId, ticketDto.getTitle(), ticketDto.getCreateId(), ticketDto.getManagerId());
        }*/

        return ticketId;
    }

    //요청수정
    public int saveTicketModify(TicketDto ticketDto) throws MessagingException {
        int resultCount =  ticketMapper.updateTicketInfo(ticketDto);
        if(resultCount != 1){
            return 0;
        }

        // 고유번호가 TicketDto에 저장됨
        int ticketId = ticketDto.getTicketId();

        // [알림, 메일] 발송 파트
        if(resultCount > 0){
            // Map<String, Object> model = new HashMap<>();
            // mailService.sendEmail(ticketDto.getManagerId(), "요청수정", "password-reset-email", model);
            alertService.sendAlert(AlertType.TICKET_STATUS ,ticketDto.getStatusCd(), ticketId, ticketDto.getTitle(), ticketDto.getCreateId(), ticketDto.getManagerId());
        }

        return ticketId;
    }

    //담당자(PM)정보
    public Map<String, Object> getManagerInfo(String companyId) {
        if (companyId == null || companyId.isEmpty()) {
            throw new IllegalArgumentException("Company ID must not be null or empty");
        }
        Map<String, Object> managerInfo = ticketMapper.getManagerInfo(companyId);
        if (managerInfo.isEmpty()) {
            throw new IllegalArgumentException("No managers found for company ID: " + companyId);
        }
        return managerInfo;
    }

    //전체 담당자 정보
    public List<TicketDto> getAllManagerOption() {
        return ticketMapper.getAllManager();
    }

    public TicketDto getTicketDetails(int ticketId) {
        return ticketMapper.selectTicketDetails(ticketId);
    }

    //진행상태 변경
    public void changeStatus(int ticketId, String newStatus, String updateId, String md) throws MessagingException {
        int resultCount = ticketMapper.updateTicketStatus(ticketId,newStatus,updateId,md);

        // [알림, 메일] 발송 파트
        if(resultCount > 0){
            TicketDto ticketDto = ticketMapper.selectTicketDetails(ticketId);

            Map<String, Object> model = new HashMap<>();
            model.put("userName", ticketDto.getCreateName());
            model.put("ticketTitle", ticketDto.getTitle());
            model.put("ticketStatus", ticketDto.getStatusCd());            
            model.put("ticketUrl", baseUrl + "/ticketView?id=" + ticketDto.getTicketId() + "&redirect=/ticketView?id=" + ticketDto.getTicketId());
            model.put("ticketId", ticketDto.getTicketId());
            model.put("companyName", ticketDto.getCompanyName());
            
            mailService.sendEmail(ticketDto.getCreateEmail(), "요청상태변경", "ticket-email", model);
            alertService.sendAlert(AlertType.TICKET_STATUS ,newStatus, ticketId, ticketDto.getTitle(), ticketDto.getCreateId(), ticketDto.getManagerId());
        }
    }

    public void chSatisfaction(int ticketId, String newStatus,String result, String feedback, String updateId) throws MessagingException {
        int resultCount = ticketMapper.chSatisfaction(ticketId,newStatus,result,feedback,updateId);
    }

    //첨부파일 저장 후 요청정보에 첨부파일 아이디 업데이트
    public void setTicketFileId(String fileId,int ticketId) {
        ticketMapper.updateTicketFileId(fileId,ticketId);
    }

    //댓글 저장
    public int addComment(CommentDto commentDto) throws MessagingException {
        int result = ticketMapper.insertTicketComment(commentDto);
        if (result != 1) {
            return 0;
        }

        int commentId = commentDto.getAnswerId(); // 혹시 getCommentId()가 맞는지 확인 필요

        TicketDto ticketDto = ticketMapper.selectTicketDetails(commentDto.getTicketId());

        Map<String, Object> model = new HashMap<>();
        model.put("userName", commentDto.getCreateName());
        model.put("ticketTitle", ticketDto.getTitle());
        model.put("ticketStatus", ticketDto.getStatusCd());
        model.put("comment", commentDto.getContent());
        model.put("ticketUrl", baseUrl + "/ticketView?id=" + ticketDto.getTicketId());
        model.put("ticketId", ticketDto.getTicketId());
        model.put("companyName", ticketDto.getCompanyName());

        // 댓글 상대방 조회 (본인 제외)
        Map<String, Object> receiver = ticketMapper.findOtherUser(commentDto.getTicketId(), commentDto.getCreateId());

      //  String receiverId = ticketMapper.findOtherUser(commentDto.getTicketId(), commentDto.getCreateId());
        if (receiver != null) {
            //진행상태 변경일경우에는 댓글알림은 보내지 않는다!
            if (!commentDto.getAlarmYN().equals("N")){
                mailService.sendEmail(receiver.get("EMAIL").toString(), "댓글알림", "ticket-comment-email", model);
            }
            alertService.sendAlert(AlertType.TICKET_COMMENT, "COMMENT", commentDto.getTicketId(),
                    commentDto.getContent(), commentDto.getCreateId(), receiver.get("ID").toString());
        } else {
         //   log.warn("댓글 알림 대상 없음: ticketId={}, createId={}", commentDto.getTicketId(), commentDto.getCreateId());
        }

        return commentId;
    }
    //첨부파일 저장 후 댓글정보에 첨부파일 아이디 업데이트
    public void setCommentFileId(String fileId,int Id) {
        ticketMapper.updateCommentFileId(fileId, Id);
    }

    public List<CommentDto> getCommentsByTicketId(int ticketId) {
        return ticketMapper.getCommentsByTicketId(ticketId);
    }

    public void deleteTicket(int ticketId) {
        ticketMapper.deleteTicket(ticketId);
    }

    public void deleteTicketAnswer(int ticketId) {
        ticketMapper.deleteTicketAnswer(ticketId);
    }

    public void deleteTicketAnswerById(int Id) {
        ticketMapper.deleteTicketAnswerById(Id);
    }

    public void deleteTicketAnswerFile(int Id) {
        ticketMapper.deleteTicketAnswerFile(Id);
    }

    public void deleteTicketAnswerFileById(int Id) {
        ticketMapper.deleteTicketAnswerFileById(Id);
    }



}
