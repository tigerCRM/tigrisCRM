package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.alert.AlertType;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.mapper.TicketMapper;
import com.tiger.crm.service.alert.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private AlertService alertService;

    // 전체 티켓 수 조회 (null 체크 포함)
    @Override
    public PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest) {

        List<Map<String, Object>> ticketList = ticketMapper.getTicketList(pagingRequest);

        int totalRecords = getTicketListCount(pagingRequest);

        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }

    // 전체 티켓 수를 조회하는 메서드
    private int getTicketListCount(PagingRequest pagingRequest) {
        Integer count = ticketMapper.getTicketListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    //티켓저장
    public int saveTicket(TicketDto ticketDto) {
        int resultCount =  ticketMapper.insertTicketInfo(ticketDto);
        if(resultCount != 1){
            return 0;
        }
        // 고유번호가 TicketDto에 저장됨
        int ticketId = ticketDto.getTicketId();
        // 알림 발송
        if(resultCount > 0){
            alertService.sendAlert(AlertType.TICKET_STATUS, ticketDto.getStatusCd(), ticketId, ticketDto.getTitle(), ticketDto.getCreateId(), ticketDto.getManagerId());
        }

        return ticketId;
    }

    //티켓수정
    public int saveTicketModify(TicketDto ticketDto) {
        int resultCount =  ticketMapper.updateTicketInfo(ticketDto);
        if(resultCount != 1){
            return 0;
        }

        // 고유번호가 TicketDto에 저장됨
        int ticketId = ticketDto.getTicketId();

        // [알림, 메일] 발송 파트
        if(resultCount > 0){
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
    public void changeStatus(int ticketId, String newStatus, String updateId) {
        int resultCount = ticketMapper.updateTicketStatus(ticketId,newStatus,updateId);

        // [알림, 메일] 발송 파트
        if(resultCount > 0){
            TicketDto ticketDto = ticketMapper.selectTicketDetails(ticketId);
            alertService.sendAlert(AlertType.TICKET_STATUS ,newStatus, ticketId, ticketDto.getTitle(), ticketDto.getCreateId(), ticketDto.getManagerId());
        }
    }

    //첨부파일 저장 후 티켓정보에 첨부파일 아이디 업데이트
    public void setTicketFileId(String fileId,int ticketId) {
        ticketMapper.updateTicketFileId(fileId,ticketId);
    }

    //댓글 저장
    public int addComment(CommentDto commentDto) {
        int result = ticketMapper.insertTicketComment(commentDto);
        if(result != 1){
            return 0;
        }

        // 고유번호가 TicketDto에 저장됨
        int commentId = commentDto.getAnswerId();

        // [알림, 메일] 발송 파트
        if(result > 0){
            // 댓글시 상대방 조회(본인 x)
            String receiverId = ticketMapper.findOtherUser(commentDto.getTicketId(), commentDto.getCreateId());
            alertService.sendAlert(AlertType.TICKET_COMMENT ,"COMMENT", commentDto.getTicketId(), commentDto.getContent(), commentDto.getCreateId(), receiverId);
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
