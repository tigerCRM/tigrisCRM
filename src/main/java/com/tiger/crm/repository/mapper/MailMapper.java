package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.mail.MailDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MailMapper {
    // 메일 발송 이력 저장
    void saveMailHist(MailDto mail);

    // 메일 발송 내역 건수 조회
    Integer getMailHistListCount(PagingRequest pagingRequest);

    // 메일 발송 내역 조회
    List<Map<String, Object>> getMailHistList(PagingRequest pagingRequest);

}
