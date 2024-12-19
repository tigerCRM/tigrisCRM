package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;


public interface TicketService {

    PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest);

    int saveTicket(TicketDto ticketDto) throws MessagingException;

    int saveTicketModify(TicketDto ticketDto);

    Map<String, Object> getManagerInfo(String companyId);

    TicketDto getTicketDetails(int ticketId);

    void changeStatus(int ticketId, String newStatus, String updateId);

    void setTicketFileId(String fileId, int ticketId);

    int addComment(CommentDto commentDto);

    List<CommentDto> getCommentsByTicketId(int ticketId);

    List<TicketDto> getAllManagerOption();

    void deleteTicket(int ticketId);

    void deleteTicketAnswer(int ticketId);

    void deleteTicketAnswerById(int Id);

    void deleteTicketAnswerFile(int ticketId);

    void deleteTicketAnswerFileById(int Id);

}


