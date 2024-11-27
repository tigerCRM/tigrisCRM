package com.tiger.crm.common.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    /**
     * 페이지 계산 로직을 처리하는 메서드
     * @param totalRecords 전체 레코드 수
     * @param recordsPerPage 페이지당 레코드 수
     * @param currentPage 현재 페이지 번호
     * @return 페이지 번호 리스트
     */
    public static List<Integer> makePages(int totalRecords, int recordsPerPage, int currentPage) {
        List<Integer> pages = new ArrayList<>();

        if (recordsPerPage <= 0) recordsPerPage = 10; // 기본값 설정
        if (currentPage <= 0) currentPage = 1; // 기본값 설정

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // 예시: 5페이지씩 묶어서 보여주기
        int startPage = ((currentPage - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        for (int i = startPage; i <= endPage; i++) {
            pages.add(i);
        }

        return pages;
    }
}