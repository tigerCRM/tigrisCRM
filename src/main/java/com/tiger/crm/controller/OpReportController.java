package com.tiger.crm.controller;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.opReport.OpReportDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.opReport.OpReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class OpReportController {

    @Autowired
    private OpReportService opReportService;
    @Autowired
    private CommonService commonService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /*
     * 운영지원 보고서 리스트 페이지(초기 접속 시)
     * */
    @GetMapping("/opReportList")
    public String getOpReportList(OpReportDto opReportDto, CompanyOptionDto companyOptionDto, HttpServletRequest request, Model model) {
        try {
            // 유저 정보 조회
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");

            // 권한 체크
            // (고객 : 해당 고객사, 관리자: 전체 고객사)
            if(loginUser.getUserClass().equals("USER")){
                companyOptionDto.setCompanyId(loginUser.getCompanyId()); // 유저의 고객사 정보
            }else{
                companyOptionDto.setCompanyId(0); // 전체조회
            }

            // 유저의 고객사 정보 조회(담당자 있음)
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption2(companyOptionDto);
            model.addAttribute("companyOptions", companyOptions);

            // 년도 셀렉트 박스 조회
            LocalDateTime currentTime = LocalDateTime.now();
            List<OpReportDto> yearList = opReportService.getYearList(currentTime.getYear());
            model.addAttribute("yearList", yearList);

            // 연도별 운영지원 보고서 목록 조회
            if( opReportDto.getYear() == 0 ){
                opReportDto.setCompanyId(loginUser.getCompanyId()); // 로그인유저 고객사번호 고정
                opReportDto.setYear(currentTime.getYear()); // 현재 년도
            }
            List<Map<String, Object>> opReportList = opReportService.getOpReportList(opReportDto);
            model.addAttribute("opReportList", opReportList);

            return "opReportList";

        } catch (Exception e) {
            throw new CustomException("운영지원 보고서 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 운영지원 보고서 리스트 페이지(검색 조건 변경시)
     * */
    @PostMapping("/opReportList")
    public String getOpReportList2(OpReportDto opReportDto, CompanyOptionDto companyOptionDto, HttpServletRequest request, Model model) {
        try {
            // 유저 정보 조회
            UserLoginDto loginUser = (UserLoginDto) request.getAttribute("user");

            // 년도 셀렉트 박스 조회
            LocalDateTime currentTime = LocalDateTime.now();

            // 연도별 운영지원 보고서 목록 조회
            if( opReportDto.getYear() == 0 ){
                opReportDto.setCompanyId(loginUser.getCompanyId()); // 로그인유저 고객사번호 고정
                opReportDto.setYear(currentTime.getYear()); // 현재 년도
            }
            List<Map<String, Object>> opReportList = opReportService.getOpReportList(opReportDto);
            model.addAttribute("opReportList", opReportList);

            return "opReportList :: opReportListFragment";

        } catch (Exception e) {
            throw new CustomException("운영지원 보고서 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    /*
     * 운영지원 보고서 상세내용 조회
     * */
    @GetMapping("/opReportContent")
    public String getOpReportContent(OpReportDto opReportDto, Model model) {
        try {

            // 운영지원 보고서 조회 > 등록일 기준 (ex : 2024.06.01~2024.06.30)
            OpReportDto resultDto  = opReportService.getOpReportContent(opReportDto);
            model.addAttribute("opReport", resultDto);

            return "fragments/opReportFragment :: opReport";

        } catch (Exception e) {
            throw new CustomException("opReportContent : 운영지원 보고서 내용조회 중 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }
    

}
