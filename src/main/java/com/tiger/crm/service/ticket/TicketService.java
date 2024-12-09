package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.CommentDto;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface TicketService {

    PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest);

    int saveTicket(TicketDto ticketDto);

    int saveTicketModify(TicketDto ticketDto);

    Map<String, Object> getManagerInfo(String companyId);

    TicketDto getTicketDetails(int ticketId);

    int changeStatus(int ticketId, String newStatus, String updateId);

    void setTicketFileId(String fileId, int ticketId);

    void addComment(int ticketId,String comment,String createId,String statusCd);

    List<CommentDto> getCommentsByTicketId(int ticketId);
}


