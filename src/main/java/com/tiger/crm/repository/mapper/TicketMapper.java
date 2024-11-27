package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.page.PagingRequest;

import com.tiger.crm.repository.dto.ticket.TicketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface TicketMapper {
    // 결과를 List<Map<String, Object>>로 받는 경우, @Param 어노테이션 사용
    List<Map<String, Object>> getTicketList(PagingRequest pagingRequest);

    Integer getTicketListCount(PagingRequest pagingRequest);

    void insertTicketInfo(TicketDto ticketDto);

    Map<String, Object> getManagerInfo(String companyId);
}