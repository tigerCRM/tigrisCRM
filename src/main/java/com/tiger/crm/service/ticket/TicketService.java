package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface TicketService {
    // 페이징 객체를 사용한 리스트
    PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest);

    boolean saveTicket(TicketDto ticketDto, List<MultipartFile> files);

    Map<String, Object> getManagerInfo(String companyId);
}


