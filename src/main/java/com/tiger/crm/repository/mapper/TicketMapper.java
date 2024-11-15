package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.ticket.TicketDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface TicketMapper {
    List<Map<String, Object>> getTicketList(@Param("searchKeyword") String searchKeyword,
                                            @Param("searchStatus") String searchStatus,
                                            @Param("searchType") String searchType,
                                            @Param("page") Integer page,
                                            @Param("startDt") String startDt,
                                            @Param("endDt") String endDt);
    int getTicketListCount(@Param("searchKeyword") String searchKeyword,
                           @Param("searchStatus") String searchStatus,
                           @Param("searchType") String searchType,
                           @Param("page") Integer page,
                           @Param("startDt") String startDt,
                           @Param("endDt") String endDt);
}