package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.TicketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TicketMapper {
    List<TicketDto> getTicketList();

}
