<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<h2 class="title">계약서 검토</h2>
<section class="view">
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
        <!-- 글 보기 영역 -->
        <div class="view--table">
            <table class="tb_basic tb_view">
                <colgroup>
                    <col style="width: 12%">
                    <col style="width: 38%">
                    <col style="width: 12%">
                    <col style="width: 38%">
                </colgroup>
                <tbody>
                    <tr>
                        <th>제목</th>
                        <td colspan="3" class="board--title">콜센터시스템 유지보수 관련 계약서 검토의 건</td>
                    </tr>
                    <tr>
                        <th>법인</th>
                        <td>지씨바이오</td>
                        <th>상태</th>
                        <td>
                            <div class="dropdown--box review-state--box">
                                <div class="dropdown--select">검토의뢰</div>
                                <ul class="dropdown--list">
                                    <li>검토중</li>
                                    <li>수정요구</li>
                                    <li>반려</li>
                                </ul>
                            </div>
                        </td>
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
                        <td>2022-04-26</td>
                    </tr>
                    <tr>
                        <th class="vt_top">검토요청내용<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3">
                            <p>
                                * 콜센터시스템 유지보수 계약 관련하여 문서 내용 검토 부탁드립니다.<br>
                                - 계약대금: ￦5,907,410<br>
                                - 콜센터시스템 유지보수 및 정기점검<br>
                                - 도급인: (주)지씨바이오 수급인 : (주)네오메카
                            </p>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">계약서 파일첨부<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3">
                            <p class="file--name"><a href="#" download>유지보수 계약서.docx</a></p>
                            <p class="file--name"><a href="#" download>유지보수 견적서.pdf</a></p>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">법무담당자<br>검토의견</th>
                        <td colspan="3"><textarea class="tb_textarea"></textarea></td>
                    </tr>
                    <tr>
                        <th class="vt_top">법무담당자<br>검토파일</th>
                        <td colspan="3">
                            <!-- 파일 첨부 영역 추가 -->
                        </td>
                    </tr>
                </tbody>
            </table>
            <table class="tb_basic tb_history">
                <colgroup>
                    <col style="width: 12%">
                    <col style="width: 88%">
                </colgroup>
                <thead>
                    <tr>
                        <th colspan="2">변경이력</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th class="vt_top">홍길동 사원<br><span class="date">2021-11-30</span></th>
                        <td class="vt_top">최초 등록</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- //글 보기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
            <button class="button primary-button">저장</button><!--
            --><button class="button light-button">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</common:page>