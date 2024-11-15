package com.tiger.crm.controller;

import com.tiger.crm.common.util.PageUtils;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.service.ticket.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /*
     * 요청관리(티켓관리)
     * 설명 : 요청관리 페이지 초기화면
     * */
    @GetMapping("/ticketlist")
    public String getTickets(@ModelAttribute TicketDto ticketDto, Model model) {
        try {
            // 입력 값 검증 및 기본값 설정
            String searchKeyword = ticketDto.getSearchKeyword() != null ? ticketDto.getSearchKeyword() : "";
            String searchType = ticketDto.getSearchType() != null ? ticketDto.getSearchType() : "";
            Integer page = ticketDto.getPage() != null ? ticketDto.getPage() : 1;
            String startDt = ticketDto.getStartDt();
            String endDt = ticketDto.getEndDt();

            // 필요한 데이터 조회
            List<Map<String, Object>> ticketList = ticketService.getTicketList(ticketDto);
            int cnt = ticketService.getTicketListCount(ticketDto);

            // 페이지 계산 (PageUtils가 null 반환 시 기본 페이지 처리)
            ArrayList<Integer> pages = PageUtils.makePages(cnt, ticketDto.getRecordCountPerPage(), page);
            if (pages == null) {
                pages = new ArrayList<>();
                pages.add(1);  // 기본 페이지 번호 설정
            }

            // 모델에 데이터 추가
            model.addAttribute("ticketList", ticketList);
            model.addAttribute("searchTicketDTO", ticketDto);  // 검색 조건 및 페이징 정보 전달
            model.addAttribute("cnt", cnt);
            model.addAttribute("pages", pages);

            return "ticketList";
        } catch (Exception e) {
            // 오류 로그 기록
            e.printStackTrace();
            model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "errorPage";  // 에러 페이지로 이동 (필요 시 별도의 오류 페이지 생성)
        }
    }

    /*
     * 요청관리(티켓관리) 검색
     * 설명 : 요청관리 페이지 검색
     * * 스크립트단에서 ajax로 호출하여 RequestBody로 data를 받아서 처리
     * */
    @PostMapping("/ticketlist")
    public String searchTickets(@RequestBody TicketDto ticketDto, Model model) {
        try {
            // 입력 값 검증 및 기본값 설정
            String searchKeyword = ticketDto.getSearchKeyword() != null ? ticketDto.getSearchKeyword() : "";
            String searchStatus = ticketDto.getSearchStatus() != null ? ticketDto.getSearchStatus() : "";
            String searchType = ticketDto.getSearchType() != null ? ticketDto.getSearchType() : "";
            Integer page = ticketDto.getPage() != null ? ticketDto.getPage() : 1;
            String startDt = ticketDto.getStartDt();
            String endDt = ticketDto.getEndDt();

            // 데이터 조회
            List<Map<String, Object>> ticketList = ticketService.getTicketList(ticketDto);
            int cnt = ticketService.getTicketListCount(ticketDto);

            // 페이지 계산
            ArrayList<Integer> pages = PageUtils.makePages(cnt, ticketDto.getRecordCountPerPage(), page);
            if (pages == null) {
                pages = new ArrayList<>();
                pages.add(1);  // 기본 페이지 번호 설정
            }

            // 모델에 데이터 추가
            model.addAttribute("ticketList", ticketList);
            model.addAttribute("cnt", cnt);
            model.addAttribute("pages", pages);

            // 부분 뷰 렌더링 (리스트 부분만 갱신)
            return "ticketList :: ticketListFragment";
        } catch (IllegalArgumentException e) {
            // 입력 값에 대한 오류 처리 (예: 유효하지 않은 파라미터)
            model.addAttribute("error", "잘못된 입력 값이 있습니다. 다시 확인해 주세요.");
            e.printStackTrace();
        } catch (Exception e) {
            // 데이터 조회 중 발생한 일반적인 오류 처리
            model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
            e.printStackTrace();
        }

        // 오류 발생 시 전체 페이지로 돌아가도록 처리
        return "ticketList";  // 기본 화면으로 이동
    }


}
