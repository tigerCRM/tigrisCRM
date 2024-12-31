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
    //요청조회
    List<Map<String, Object>> getTicketList(PagingRequest pagingRequest);

    //요청카운트
    Integer getTicketListCount(PagingRequest pagingRequest);

    //요청등록
    int insertTicketInfo(TicketDto ticketDto);

    //요청수정
    int updateTicketInfo(TicketDto ticketDto);

    //고객사 담당자조회
    Map<String, Object> getManagerInfo(String companyId);

    //전체 고객사 담당자조회
    List<TicketDto> getAllManager();

    //요청 상세 정보 조회
    TicketDto selectTicketDetails(int ticketId);

    //요청 상태 업데이트
    int updateTicketStatus(int ticketId, String newStatus, String updateId);

    //요청 첨부파일 ID 업데이트
    int updateTicketFileId(String fileId, int ticketId);

    //댓글저장
    int insertTicketComment(CommentDto commentDto);

    List<CommentDto> getCommentsByTicketId(int ticketId);

    void deleteTicket(int ticketId);

    void deleteTicketAnswer(int ticketId);

    void deleteTicketAnswerFile(int ticketId);

    void deleteTicketAnswerById(int tId);

    void deleteTicketAnswerFileById(int Id);

    int updateCommentFileId(String fileId, int Id);

    // 댓글시 상대방 조회(본인 x)
    String findOtherUser(Integer ticketId, String commentId);

}