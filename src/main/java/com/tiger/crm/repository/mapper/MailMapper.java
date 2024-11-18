package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.mail.MailDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailMapper {
    // 메일 발송 이력 저장
    void saveMailHist(MailDto mail);
}
