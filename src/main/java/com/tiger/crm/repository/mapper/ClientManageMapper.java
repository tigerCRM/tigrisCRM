package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClientManageMapper {

    // 고객사 카운트
    Integer getCompanyListCount(PagingRequest pagingRequest);

    // 고객 목록 조회
    List<Map<String, Object>> getCompanyList(PagingRequest pagingRequest);

    // 고객사 상세 조회
    List<ClientManageDto> getCompanyDetail(ClientManageDto clientManageDto);

    // 고객 목록 조회
    List<Map<String, Object>> getClientList(PagingRequest pagingRequest);

    // 고객 상세 조회
    List<ClientManageDto> getClientDetail(ClientManageDto clientManageDto);

    // 고객 카운트
    Integer getClientListCount(PagingRequest pagingRequest);

    // 신규 고객사 등록
    void createCompany(ClientManageDto clientManageDto);

    // 신규 고객 등록
    void createClient(@Param("clientManageDto") ClientManageDto clientManageDto);

    // 고객사 수정
    void updateCompany(ClientManageDto clientManageDto);

    // 사용자 수정
    void updateClient(ClientManageDto clientManageDto);
    // 비밀번호 수정
    void changePassword(ClientManageDto clientManageDto);

    //연락망
    List<ClientManageDto> getContacts();

    // 그룹 조회
    List<Map<String, Object>> getGroupList(PagingRequest pagingRequest);

    // 그룹 카운트
    Integer getGroupListCount(PagingRequest pagingRequest);

    // 고객사 상세 조회
    List<ClientManageDto> getGroupDetail(ClientManageDto clientManageDto);

    //권한 관리자 리스트
    List<ClientManageDto> getGroupAuthList(ClientManageDto clientManageDto);

    // 신규 권한 그룹 등록
    void createGroup(ClientManageDto clientManageDto);
    // 고객사 수정
    void updateGroup(ClientManageDto clientManageDto);


}
