package com.tiger.crm.repository.dto.page;

import com.tiger.crm.common.util.PageUtils2;
import lombok.Getter;
import java.util.List;

@Getter
public class PagingResponse<T> {
    private final List<T> dataList;     // 페이지 데이터 리스트
    private final int totalElements;   // 전체 데이터 개수
    private final int totalPages;      // 전체 페이지 수
    private final int currentPage;     // 현재 페이지 번호
    private final int pageSize;        // 한 페이지에 보여줄 데이터 개수
    private final List<Integer> pages; // 페이지 번호 리스트

    public PagingResponse(List<T> dataList, int totalElements, PagingRequest pagingRequest) {
        this.dataList = dataList;
        this.totalElements = totalElements;
        this.pageSize = pagingRequest.getSize();
        this.currentPage = pagingRequest.getPage();
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.pages = PageUtils2.makePages(totalElements, pageSize, currentPage);
    }
}
