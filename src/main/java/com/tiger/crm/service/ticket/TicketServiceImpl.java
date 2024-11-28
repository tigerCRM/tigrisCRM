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

        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }

    // 전체 티켓 수를 조회하는 메서드
    private int getTicketListCount(PagingRequest pagingRequest) {
        Integer count = ticketMapper.getTicketListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    //티켓저장
    public boolean saveTicket(TicketDto ticketDto, List<MultipartFile> files) {
        try {
            ticketMapper.insertTicketInfo(ticketDto);
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    //첨부파일 저장 로직 추가 예정
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("티켓 저장 중 오류가 발생했습니다.", e);  // 예외를 던짐
        }
        return true;
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
}
