package com.tiger.crm.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface TicketMapper {
    List<Map<String, Object>> getTicketList();
    List<Map<String, Object>> searchTickets(String searchKeyword);
}