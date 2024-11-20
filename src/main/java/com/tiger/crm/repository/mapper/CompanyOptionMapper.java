package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyOptionMapper {
    /*CompanyOptionMapper
    * 작성자 : 제예솔
    * 설명 : getAllCompany - 사용중인 모든 회사 코드 및 이름 가져옴*/
    List<CompanyOptionDto> getAllCompany();
}
