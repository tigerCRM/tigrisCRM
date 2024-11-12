package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.TicketDto;
import com.tiger.crm.repository.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {
    User getUser(String id, String password);
}
