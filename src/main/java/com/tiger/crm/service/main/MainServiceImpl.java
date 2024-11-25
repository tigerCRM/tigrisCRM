package com.tiger.crm.service.main;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mainMapper;

    // 메인페이지 요청내역 조회
    @Override
    public List<Map<String, Object>> getMainTicketList(UserLoginDto user) {
            List<Map<String, Object>> ticketList = mainMapper.getMainTicketList(user);
            return ticketList;
    }

}
