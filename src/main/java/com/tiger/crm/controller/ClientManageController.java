package com.tiger.crm.controller;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.client.ClientManageService;
import com.tiger.crm.service.common.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClientManageController {

    @Autowired
    private ClientManageService clientManageService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SpringTemplateEngine templateEngine;

    /*
     * 고객사 목록 페이지(최초 접속 시)
     */
    @GetMapping("/clientManage")
    public String getOpReportList(@ModelAttribute PagingRequest pagingRequest, ClientManageDto clientManageDto, Model model) {
        try {
            // 최초 접속 파라미터
            clientManageDto.setCompanyId(1);

            // 1. 고객사 목록 조회
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getCompanyList(pagingRequest);
            model.addAttribute("companyList", pageResponse);

            // 2. 고객사 상세 조회
            List<ClientManageDto> companyDetail = clientManageService.getCompanyDetail(clientManageDto);
            model.addAttribute("companyDetail", companyDetail);

            return "clientManage";

        } catch (Exception e) {
            throw new CustomException("고객사 초기 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객사 목록 조회(상세검색 시)
     */
    @PostMapping("/companyListData")
    @ResponseBody
    public Map<String, Object> getCompanyListData(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {

            // 1. 고객사 목록 조회
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getCompanyList(pagingRequest);
            model.addAttribute("companyList", pageResponse);

            // 2. Model을 Context로 변환
            Context context = new Context();
            context.setVariable("list", pageResponse);  // PagingResponse 객체를 "list"로 설정
            context.setVariable("paginationUrl", "companyListData");  // 페이징 URL을 설정

            // 3. 페이징 HTML 생성
            String paginationHtml = templateEngine.process("fragments/pagination", context);

            // 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("companyList", pageResponse.getDataList());  // 고객 목록
            response.put("paginationHtml", paginationHtml);          // 페이징 HTML

            return response;

        } catch (Exception e) {
            throw new CustomException("고객사 목록(상세검색) 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객사 상세 조회
     */
    @GetMapping("/companyDetailData")
    @ResponseBody
    public  Map<String, Object> getCompanyDetailData(ClientManageDto clientManageDto) {
        try {

            // 1. 고객사 상세 조회
            List<ClientManageDto> companyDetail = clientManageService.getCompanyDetail(clientManageDto);

            // 2. 데이터를 Map에 담기
            Map<String, Object> response = new HashMap<>();
            if (!companyDetail.isEmpty()) {
                ClientManageDto detail = companyDetail.get(0); // 첫 번째 고객사 정보 가져오기
                response.put("companyId", detail.getCompanyId());
                response.put("companyName", detail.getCompanyName());
                response.put("managerId", detail.getManagerId());
            }

            return response;

        } catch (Exception e) {
            throw new CustomException("고객사의 상세 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객 목록 조회
     */
    @PostMapping("/clientListData")
    @ResponseBody
    public Map<String, Object> getClientListData(@ModelAttribute PagingRequest pagingRequest, Model model) {
        try {

            // 1. 고객 목록 조회 (페이징된 데이터)
            PagingResponse<Map<String, Object>> pageResponse = clientManageService.getClientList(pagingRequest);
            model.addAttribute("clientList", pageResponse);

            // 2. Model을 Context로 변환
            Context context = new Context();
            context.setVariable("list", pageResponse);  // PagingResponse 객체를 "list"로 설정
            context.setVariable("paginationUrl", "clientListData");  // 페이징 URL을 설정

            // 3. Thymeleaf 템플릿을 HTML로 렌더링
            String paginationHtml = templateEngine.process("fragments/pagination", context);

            // 4. JSON 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("clientList", pageResponse.getDataList()); // 데이터 목록
            response.put("paginationHtml", paginationHtml);         // 렌더링된 페이징 HTML

            return response;

        } catch (Exception e) {
            throw new CustomException("고객의 목록 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 고객사 상세 조회
     */
    @GetMapping("/clientDetailData")
    @ResponseBody
    public  Map<String, Object> getClientDetailData(ClientManageDto clientManageDto) {
        try {

            // 1. 고객사 상세 조회
            List<ClientManageDto> clientDetail = clientManageService.getClientDetail(clientManageDto);

            // 2. 데이터를 Map에 담기
            Map<String, Object> response = new HashMap<>();
            if (!clientDetail.isEmpty()) {
                ClientManageDto detail = clientDetail.get(0); // 첫 번째 고객사 정보 가져오기
                response.put("userName", detail.getUserName());
                response.put("userId", detail.getUserId());
                response.put("userClass", detail.getUserClass());
                response.put("email", detail.getEmail());
                response.put("phone", detail.getPhone());
            }

            return response;

        } catch (Exception e) {
            throw new CustomException("고객의 상세 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }
    
    /*
     * 신규 고객사 등록
     */
    @PostMapping("/createCompany")
    @ResponseBody
    public Map<String, Object> createCompany(@RequestBody ClientManageDto clientManageDto, HttpServletRequest request) {
        try {
            // 1. 사용자값 추출
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");
            clientManageDto.setUserId(loginUser.getUserId()); // 수정자 용도
            
            // 2. 신규 고객사 등록
            clientManageService.createCompany(clientManageDto);

        }catch (Exception e) {
            throw new CustomException("신규 고객사 등록 중 오류가 발생했습니다.", e);
        }
        return Map.of();
    }
}
