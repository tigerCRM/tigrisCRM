package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest) {

        List<Map<String, Object>> ticketList = ticketMapper.getTicketList(pagingRequest);
        // 전체 티켓 수 조회 (null 체크 포함)
        int totalRecords = getTicketListCount(pagingRequest);

        // 페이징 응답 객체 생성
        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }
    // 전체 티켓 수를 조회하는 메서드
    private int getTicketListCount(PagingRequest pagingRequest) {
        Integer count = ticketMapper.getTicketListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    public boolean saveTicket(TicketDto ticketDto, List<MultipartFile> files) {
        // Save ticketDto data to the database
        ticketMapper.insertTicketInfo(ticketDto);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // Save each file, e.g., save to a directory or database
                String fileName = file.getOriginalFilename();
                // Save logic here
            }
        }

        return true;
    }

}
