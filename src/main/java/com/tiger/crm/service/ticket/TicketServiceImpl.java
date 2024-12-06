package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.alert.AlertDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.TicketMapper;
import com.tiger.crm.service.alert.AlertService;
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
    public int saveTicket(TicketDto ticketDto, List<MultipartFile> files) {
        int resultCount =  ticketMapper.insertTicketInfo(ticketDto);
        if(resultCount != 1){
            return 0;
        }
        // 고유번호가 TicketDto에 저장됨
        int ticketId = ticketDto.getTicketId();

        // 알림 발송
        if(resultCount > 0){
            AlertDto alertDto = new AlertDto();
            alertDto.setAlertType(ticketDto.getStatusCd()); // 상태코드
            alertDto.setAlertObjectId(ticketId); // 요청사항 고유번호
            alertDto.setContent(ticketDto.getContent()); // 내용
            alertDto.setSenderId(ticketDto.getCreateId()); // 발송인 아이디
            alertDto.setReceiverId(ticketDto.getManagerId()); // 수령인 아이디
            alertService.createAlert(alertDto);
        }

        return ticketDto.getTicketId();
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

    public TicketDto getTicketDetails(int ticketId) {
        return ticketMapper.selectTicketDetails(ticketId);
    }

    public int changeStatus(int ticketId, String newStatus, String updateId) {
        return ticketMapper.updateTicketStatus(ticketId,newStatus,updateId);
    }

    //첨부파일 저장 후 boardTable 에 첨부파일 아이디 업데이트
    public void setTicketFileId(String fileId,int ticketId) {
        ticketMapper.updateTicketFileId(fileId,ticketId);
    }
}
