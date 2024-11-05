<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<h2 class="title">계약서 검토</h2>
<section class="write">
    <!-- 결재 서명란 -->
    <div class="signature--box">
        <table class="signature--table">
            <colgroup>
                <col style="width: 33.3%">
                <col style="width: 33.3%">
                <col style="width: 33.3%">
            </colgroup>
            <thead>
                <tr>
                    <th>담당자</th>
                    <th>팀장</th>
                    <th>법무담당자</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>홍길동</td>
                    <td>홍길서</td>
                    <td>법무담당자</td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tfoot>
        </table>
    </div>
    <!-- //결재 서명란 -->

    <div class="content">
        <!-- 글 쓰기 영역 -->
        <div class="write--table">
            <table class="tb_basic">
                <colgroup>
                    <col style="width: 10%">
                    <col style="width: 40%">
                    <col style="width: 15%">
                    <col style="width: 35%">
                </colgroup>
                <tbody>
                    <tr>
                        <th>제목</th>
                        <td colspan="3"><input type="text" class="tb_input" placeholder="제목을 입력하세요"></td>
                    </tr>
                    <tr>
                        <th>부서명</th>
                        <td>시스템운영팀</td>
                        <th>검토의뢰일</th>
                        <td>2022-04-21</td>
                    </tr>
                    <tr>
                        <th>등록자</th>
                        <td>홍길동</td>
                        <th>검토완료요청일</th>
                        <td>
                            <div class="dropdown--box request-date--box">
                                <div class="dropdown--select">2022-04-26</div>
                                <div class="dropdown--list">
                                    <!-- 캘린더 표시 -->
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">검토요청내용<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3"><textarea class="tb_textarea"></textarea></td>
                    </tr>
                    <tr>
                        <th class="vt_top">계약서 파일첨부<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3">
                            <!-- 파일 첨부 영역 추가 -->
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- //글 쓰기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
            <button class="button primary-button">검토요청</button><!--
            --><button class="button light-button">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</common:page>