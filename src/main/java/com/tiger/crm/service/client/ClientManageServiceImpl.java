package com.tiger.crm.service.client;

import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.ClientManageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ClientManageServiceImpl implements ClientManageService {

    @Autowired
    private ClientManageMapper clientManageMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    // 고객사 목록 조회
    @Override
    public PagingResponse<Map<String, Object>> getCompanyList(PagingRequest pagingRequest) {
        List<Map<String, Object>> companyList = clientManageMapper.getCompanyList(pagingRequest);

        int totalRecords = getCompanyListCount(pagingRequest);

        return new PagingResponse<>(companyList, totalRecords, pagingRequest);
    }

    // 전체 요청 수를 조회하는 메서드
    private int getCompanyListCount(PagingRequest pagingRequest) {
        Integer count = clientManageMapper.getCompanyListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    // 고객사 상세 조회
    @Override
    public List<ClientManageDto> getCompanyDetail(ClientManageDto clientManageDto) {
        return clientManageMapper.getCompanyDetail(clientManageDto);
    }

    // 고객 목록 조회
    @Override
    public PagingResponse<Map<String, Object>> getClientList(PagingRequest pagingRequest) {

        // 1. 데이터 목록 조회
        List<Map<String, Object>> clientList = clientManageMapper.getClientList(pagingRequest);

        // 2. 총 레코드 수 계산
        int totalRecords = getClientListCount(pagingRequest);

        // 3. PagingResponse 생성
        return new PagingResponse<>(clientList, totalRecords, pagingRequest);
    }

    // 고객 목록 카운트
    private int getClientListCount(PagingRequest pagingRequest) {
        Integer count = clientManageMapper.getClientListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    // 고객 상세 조회
    @Override
    public List<ClientManageDto> getClientDetail(ClientManageDto clientManageDto) {
        return clientManageMapper.getClientDetail(clientManageDto);
    }

    // 신규 고객사 등록
    @Override
    public void createCompany(ClientManageDto clientManageDto) {
        clientManageMapper.createCompany(clientManageDto);
    }

    // 신규 고객 등록
    @Override
    public void createClient(ClientManageDto clientManageDto) {

        // 관리자가 고객을 임의로 생성하는 경우 초기 패스워드로 저장
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String tempPwd = "@" + today.format(formatter) ;


        clientManageMapper.createClient(clientManageDto, passwordEncoder.encode(tempPwd));
    }


}
