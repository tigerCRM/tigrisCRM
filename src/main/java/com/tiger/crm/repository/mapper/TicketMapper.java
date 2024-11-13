package com.tiger.crm.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface TicketMapper {
    //List<Map<String, Object>> getTicketList();
    List<Map<String, Object>> getTicketList(@Param("searchKeyword") String searchKeyword);
}