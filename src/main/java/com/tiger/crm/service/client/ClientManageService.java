package com.tiger.crm.service.client;

import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;

import java.util.List;
import java.util.Map;

public interface ClientManageService {

    // 고객사 목록 조회
    PagingResponse<Map<String, Object>> getCompanyList(PagingRequest pagingRequest);

    // 고객사 상세 조회
    List<ClientManageDto> getCompanyDetail(ClientManageDto clientManageDto);

    // 고객 목록 조회
    PagingResponse<Map<String, Object>> getClientList(PagingRequest pagingRequest);

    // 고객 상세 조회
    List<ClientManageDto> getClientDetail(ClientManageDto clientManageDto);

    // 신규 고객사 등록
    void createCompany(ClientManageDto clientManageDto);

    // 고객사 수정
    void updateCompany(ClientManageDto clientManageDto);

    // 신규 사용자 등록
    void createClient(ClientManageDto clientManageDto);

    //사용자 수정
    void updateClient(ClientManageDto clientManageDto);

    // 고객 목록 조회
    List<ClientManageDto> getContacts();

    //비밀번호 수정
    void changePassword(ClientManageDto clientManageDto);

    // 그룹 목록 조회
    PagingResponse<Map<String, Object>> getGroupList(PagingRequest pagingRequest);

    // 그룹 상세 조회
    List<ClientManageDto> getGroupDetail(ClientManageDto clientManageDto);

    // 그룹 권한 관리자 조회
    List<ClientManageDto> getGroupAuthList(ClientManageDto clientManageDto);

    // 신규 그룹 권한 등록
    void createGroup(ClientManageDto clientManageDto);

    // 그룹 권한 수정
    void updateGroup(ClientManageDto clientManageDto);

}
