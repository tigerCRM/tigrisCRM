package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.page.PagingRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
시스템관리 매퍼
* 작성자 : 제예솔
*/
@Mapper
public interface SystemBoardMapper {

    List<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest);

    Integer getSystemBoardListCount(PagingRequest pagingRequest);
}
