<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<style>
#btnDelete { float:left; }
</style>

<form id="feedReadForm">
<input type="hidden" name="communityId" 	value="${communityId}" />
<input type="hidden" name="communityName" 	value="${communityInfo.communityName}" />
<input type="hidden" name="feedId" 			value="${feed.feedId}" />

<h2 class="title">${communityInfo.communityName}</h2>
<section class="view">
	<c:if test="${feed.feedState eq 'REVIEW_COMPLETED'}">
		<button type="button" class="print-button" onclick="window.print()"></button>
	</c:if>
    <!-- 결재 서명란 -->
    <c:if test="${communityInfo.signatureYn eq 'Y'}">
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
	                    <td>${feed.userName}</td>
	                    <td>${feed.teamLeaderNm}</td>
	                    <td>${feed.reviewerName}</td>
	                </tr>
	            </tbody>
	            <tfoot>
	                <tr>
	                    <td style="text-align: center;">${createDate}</td>
	                    <td style="text-align: center;">${approvalDate}</td>
	                    <td style="text-align: center;">${completeDate}</td>
	                </tr>
	            </tfoot>
	        </table>
	    </div>
    </c:if>
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
                        <c:choose>
			            	<c:when test="${communityInfo.communityName eq '계약서 검토'}"><th>계약서명</th></c:when>
			            	<c:otherwise><th>제목</th></c:otherwise>
			            </c:choose>
                        <td colspan="3" class="board--title">${feed.title}</td>
                    </tr>
                    <tr>
		            	<c:if test="${communityInfo.communityName eq '계약서 검토'}">
				            <th>계약상대방</th>
	                        <td colspan="3">${feed.contractPartner}</td>
		            	</c:if>
                    </tr>
                    <tr>
                        <th>상태</th>
                        <td colspan="3">${feed.feedStateName}</td>
                    </tr>
                    <tr>
                        <th>부서명</th>
                        <td>${feed.orgName}</td>
                        <th>검토의뢰일</th>
                        <td>${feed.requestDateStr}</td>
                    </tr>
                    <tr>
                        <th>등록자</th>
                        <td>${feed.userName}</td>
                        <th>검토완료요청일</th>
                        <td>${feed.reviewDateStr}</td>
                    </tr>
                    <tr>
                        <th class="vt_top">검토요청내용<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3">
                            <p style="white-space:break-spaces;">${feed.requestContents}</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top" style="border-bottom: 3px solid var(--cool-gray);">파일첨부<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3" style="border-bottom: 3px solid var(--cool-gray);">
                        	<c:forEach items="${requestFileList}" var="file">
                        		<p class="file--name"><a href="/file/download/${file.fileId}/${file.seq}">${file.originFileName} <small style="color:var(--cool-gray-regular)">(${file.createDtStr})</small></a></p>
                        	</c:forEach>
                        </td>
                    </tr>
                    <c:if test="${!empty feed.reviewContents}">
                    	<tr>
	                        <th class="vt_top">법무담당자<br>검토의견</th>
	                        <td colspan="3">
	                            <p style="white-space:break-spaces;">${feed.reviewContents}</p>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th class="vt_top">법무담당자<br>검토파일</th>
	                        <td colspan="3">
	                            <c:forEach items="${reviewFileList}" var="reviewFile">
	                        		<p class="file--name"><a href="/file/download/${reviewFile.fileId}/${reviewFile.seq}">${reviewFile.originFileName} <small style="color:var(--cool-gray-regular)">(${reviewFile.createDtStr})</small></a></p>
	                        	</c:forEach>
	                        </td>
	                    </tr>
		            </c:if>
                </tbody>
            </table>

			<!-- 댓글 영역 -->
            <div class="comment--box" id="readReply">
            </div>
			<!-- //댓글 영역 -->

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
                	<c:forEach items="${historyList}" var="history">
	                    <tr>
	                        <th class="vt_top">${history.userName} ${history.jobGrade}<br><span class="date"><fmt:formatDate value='${history.createDt}' pattern='yyyy-MM-dd HH:mm:ss' /></span></th>
	                        <td class="vt_top">[${history.historyType}] ${history.comment}</td>
	                    </tr>
                	</c:forEach>
                </tbody>
            </table>
        </div>
        <!-- //글 보기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
        	<!-- 법무담당자 -->
       		<c:if test="${userInfo.orgCode eq legalOrgCode}">
       			<!-- 검토의뢰 상태가 아닌 모든 경우에서 검토가능 -->
	       		<c:if test="${feed.feedState ne 'WAITING_FOR_APPROVAL'}">
		            <button type="button" id="btnReview" class="button primary-button">검토 및 수정</button>
	       		</c:if>
	       		<!-- 검토의뢰, 수정요구 상태가 아닌 경우 법무담당자만 게시글 삭제 가능 -->
	       		<c:if test="${feed.feedState ne 'WAITING_FOR_APPROVAL' && feed.feedState ne 'REQUEST_FOR_REVIEW'}">
	            	<button type="button" id="btnDelete" class="button default-button">삭제</button>
	       		</c:if>
       		</c:if>
        	<!-- 의뢰자의 팀장 -->
       		<c:if test="${userInfo.userId eq feed.teamLeaderId}">
	       		<c:if test="${feed.feedState eq 'WAITING_FOR_APPROVAL'}">
		            <button type="button" id="btnApproval" class="button primary-button">승인</button>
	       		</c:if>
       		</c:if>
        	<!-- 의뢰자 -->
       		<c:if test="${userInfo.userId eq feed.userId}">
	       		<!-- 승인대기, 검토의뢰 상태에서만 작성자 본인이 삭제 가능 -->
	       		<c:if test="${feed.feedState eq 'WAITING_FOR_APPROVAL' || feed.feedState eq 'REQUEST_FOR_REVIEW'}">
	            	<button type="button" id="btnDelete" class="button default-button">삭제</button>
	       		</c:if>
       			<!-- 승인대기, 수정요구 상태에서만 수정 가능 -->
	       		<c:if test="${feed.feedState eq 'WAITING_FOR_APPROVAL' || feed.feedState eq 'REQUEST_FOR_CORRECTION'}">
	            	<button type="button" id="btnModify" class="button primary-button">수정</button>
	       		</c:if>
       			<!-- 무료법률상담의 경우 검토의뢰 상태에서 수정 가능 -->
	       		<c:if test="${feed.feedState eq 'REQUEST_FOR_REVIEW'}">
	            	<button type="button" id="btnModify" class="button primary-button">수정</button>
	       		</c:if>
       		</c:if>

            <button type="button" id="btnCancel" class="button light-button">목록으로</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
$(document).ready(function(){
	// 무료법률상담 커뮤니티인 경우 인쇄버튼을 오른쪽으로 배치
	var communityName = $("input[name=communityName]").val();
	if(communityName == "무료법률상담")
	{
		$(".view .print-button").css("right", "0px");
	}


	var feedId = $("input[name=feedId]").val();
	getReplyList("readReply", feedId);

	// 목록으로 버튼
	$("#btnCancel").click(function(){
		var communityId = $("input[name=communityId]").val();
		location.href="/feed?communityId="+communityId;
	});

	// 수정 버튼
	$("#btnModify").click(function(){
		$("#feedReadForm").attr("action", "/feed/modify");
		$("#feedReadForm").attr("method", "GET");
		$("#feedReadForm").submit();
	});

	// 삭제 버튼
	$("#btnDelete").click(function(){
		if( confirm('현재 게시물을 삭제하시겠습니까?') )
		{
			$("#feedReadForm").attr("action", "/feed/delete");
			$("#feedReadForm").attr("method", "GET");
			$("#feedReadForm").submit();
		}
	});

	// 승인 버튼
	$("#btnApproval").click(function(){
		var communityId = $("input[name=communityId]").val();
		var feedId 		= $("input[name=feedId]").val();

		$.ajax({
			 url: "/feed/approval"
			,type: "POST"
			,data: {
				 communityId: communityId
				,feedId: feedId
			}
			,success: function(data) {
				if(data.code == 0)
				{
					alert("승인이 완료되었습니다.");
					location.href="/feed?communityId="+communityId;
				}
				else
				{
					alert("승인이 실패하였습니다.");
				}
			}
		});
	});

	// 검토 버튼
	$("#btnReview").click(function(){
		$("#feedReadForm").attr("action", "/feed/review");
		$("#feedReadForm").attr("method", "GET");
		$("#feedReadForm").submit();
	});

	// 인쇄화면이 닫혔을 때 인쇄이력을 DB에 저장
	window.onafterprint = function() {
		var feedId = $("input[name=feedId]").val();
		$.ajax({
			 url: "/feed/printHistory"
			,type: "POST"
			,data: {
				feedId: feedId
			}
		});
	};

});
</script>

</common:page>