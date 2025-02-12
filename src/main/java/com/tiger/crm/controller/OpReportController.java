package com.tiger.crm.controller;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.opReport.OpReportDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.opReport.OpReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OpReportController {

    @Autowired
    private OpReportService opReportService;
    @Autowired
    private CommonService commonService;

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
            model.addAttribute("userClass", loginUser.getUserClass());
            // 유저의 고객사 정보 조회(담당자 있음)
            List<CompanyOptionDto> companyOptions = commonService.getCompanyOption2(companyOptionDto);
            model.addAttribute("companyOptions", companyOptions);

            // 년도 셀렉트 박스 조회
            LocalDateTime currentTime = LocalDateTime.now();
            List<OpReportDto> yearList = opReportService.getYearList(currentTime.getYear());
            model.addAttribute("yearList", yearList);

            return "opReportList";

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
            model.addAttribute("opReportCnt", resultDto.getDetails().size());
            model.addAttribute("opReport", resultDto);

            return "fragments/opReportFragment :: opReport";

        } catch (Exception e) {
            throw new CustomException("opReportContent : 운영지원 보고서 내용조회 중 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }
    

}
