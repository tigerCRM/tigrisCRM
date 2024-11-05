<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<h2 class="title">계약서 검토</h2>
<section class="view">
    <button class="print-button" onclick="window.print()"></button>
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
                        <td>검토의뢰</td>
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
                        <th class="vt_top">검토요청내용</th>
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
                        <th class="vt_top">계약서 파일첨부</th>
                        <td colspan="3">
                            <p class="file--name"><a href="#" download>유지보수 계약서.docx</a></p>
                            <p class="file--name"><a href="#" download>유지보수 견적서.pdf</a></p>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="comment--box">
                <div class="comment--title">댓글</div>
                <div class="comment--list">
                    <ul>
                        <li class="comment--item">
                            <div class="comment-item--header">
                                <div class="comment-item--info">
                                    <span class="com-name">홍길동 과장</span>
                                    <span class="com-date">2021-11-30</span>
                                </div>
                            </div>
                            <!-- 댓글 내용 -->
                            <div class="comment-item--content">
                                <p>올려주신 내용은 모두 확인했습니다! 감사합니다.</p>
                            </div>
                            <!-- //댓글 내용 -->
                        </li>
                        <li class="comment--item">
                            <div class="comment-item--header">
                                <div class="comment-item--info">
                                    <span class="com-name">홍길동 과장</span>
                                    <span class="com-date">2021-11-30</span>
                                </div>
                            </div>
                            <!-- 댓글 내용 -->
                            <div class="comment-item--content">
                                <p>올려주신 내용은 모두 확인했습니다! 감사합니다.</p>
                            </div>
                            <!-- //댓글 내용 -->
                        </li>
                    </ul>
                </div>
            </div>
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
                    <tr>
                        <th class="vt_top">홍길서 팀장<br><span class="date">2021-11-31</span></th>
                        <td class="vt_top">결재 완료</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- //글 보기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
            <button class="button default-button">목록으로</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</common:page>