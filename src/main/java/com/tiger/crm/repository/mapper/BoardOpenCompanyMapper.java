package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardOpenCompanyMapper {

    int insertBoardOpenCompany(BoardOpenCompanyDto boardOpenCompanyDto);
}
