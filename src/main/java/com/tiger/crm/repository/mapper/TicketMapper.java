package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.page.PagingRequest;

import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TicketMapper {
    //티켓조회
    List<Map<String, Object>> getTicketList(PagingRequest pagingRequest);

    //티켓카운트
    Integer getTicketListCount(PagingRequest pagingRequest);

    //티켓등록
    int insertTicketInfo(TicketDto ticketDto);

    //티켓수정
    int updateTicketInfo(TicketDto ticketDto);

    //고객사 담당자조회
    Map<String, Object> getManagerInfo(String companyId);

    //전체 고객사 담당자조회
    List<TicketDto> getAllManager();

    //티켓 상세 정보 조회
    TicketDto selectTicketDetails(int ticketId);

    //티켓 상태 업데이트
    int updateTicketStatus(int ticketId, String newStatus, String updateId);

    //티켓 첨부파일 ID 업데이트
    int updateTicketFileId(String fileId, int ticketId);

    //댓글저장
    void insertTicketComment(int ticketId, String comment, String createId, String statusCd);

    List<CommentDto> getCommentsByTicketId(int ticketId);

    void deleteTicket(int ticketId);

    void deleteTicketAnswer(int ticketId);

    void deleteTicketAnswerById(int ticketId);
}