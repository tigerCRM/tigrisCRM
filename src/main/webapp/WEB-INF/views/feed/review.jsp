<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<form id="reviewForm" action="/feed/createReview" method="POST">
<input type="hidden" name="communityId" 	value="${communityId}" />
<input type="hidden" name="communityName" 	value="${communityInfo.communityName}" />
<input type="hidden" name="userId" 			value="${feed.userId}" />
<input type="hidden" name="teamLeaderId" 	value="${feed.teamLeaderId}" />
<input type="hidden" name="feedId" 			value="${feed.feedId}" />
<input type="hidden" name="prevFeedState" 	value="${feed.feedState}" />
<input type="hidden" name="feedState" 		value="${feed.feedState}" />
<input type="hidden" name="reviewFileId" 	value="${feed.reviewFileId}"/>

<h2 class="title">${communityInfo.communityName}</h2>
<section class="view">
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
                    <c:if test="${communityInfo.communityName eq '계약서 검토'}">
	                    <tr>
			            	<th>계약상대방</th>
	                        <td colspan="3">${feed.contractPartner}</td>
	                    </tr>
                    </c:if>
                    <tr>
                        <th>상태</th>
                        <td colspan="3">
                        	<div class="dropdown--box review-state--box">
				                <div class="dropdown--select">${feed.feedStateName}</div>
				                <ul class="dropdown--list">
				                	<c:if test="${feed.feedState eq 'REQUEST_FOR_REVIEW'}"><li id="REQUEST_FOR_REVIEW">검토의뢰</li></c:if>
				                    <li id="UNDER_REVIEW">검토중</li>
				                    <c:if test="${communityInfo.communityName ne '무료법률상담'}">
					                    <li id="REQUEST_FOR_CORRECTION">수정요구</li>
					                    <li id="REJECT">반려</li>
				                    </c:if>
				                    <li id="REVIEW_COMPLETED">검토완료</li>
				                </ul>
                            </div>
						</td>
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
                    <tr>
                        <th class="vt_top">법무담당자<br>검토의견</th>
                        <td colspan="3"><textarea spellcheck="false" class="tb_textarea" name="reviewContents">${feed.reviewContents}</textarea></td>
                    </tr>
                    <tr>
                        <th class="vt_top">법무담당자<br>검토파일</th>
                        <td colspan="3">
                            <!-- 파일 첨부 영역 추가 -->
                            <div class="input_file">
	                            <div id="reviewFileUploader" style="display:block;">
									<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
								</div>
							</div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <!-- 댓글 영역 -->
            <div class="comment--box" id="reviewReply">
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
            <button type="button" id="btnSave" class="button primary-button">저장</button>
            <button type="button" id="btnCancel" class="button light-button">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
var reviewFileUploader;

function reviewFileUploadCallback() {
	$("#reviewForm").submit();
}

function reviewAfterAuthHandler() {
	reviewFileUploader.start();
}

$(document).ready(function(){
	var feedId = $("input[name=feedId]").val();
	getReplyList("reviewReply", feedId);

	// 게시글의 현재상태는 select option에서 보이지 않게 함
	$("#"+$("input[name=prevFeedState]").val()).css("display", "none");

	var firstUploaded 	= false; // fileId 생성을 위한 값
	var fileId 			= $("input[name=reviewFileId]").val();

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'fileId': fileId,
		'uploaderId': 'reviewFileUploader',
		'callback': reviewFileUploadCallback,
		'authHandlerName': 'reviewAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=reviewFileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	reviewFileUploader = loadPluploadQueue(uploaderOptions);

	// 상태 select 클릭 시
	$(".review-state--box .dropdown--select").click(function(){
		var cssDisplayValue = $(".review-state--box .dropdown--list").css("display");

		if(cssDisplayValue == "none") { $(".review-state--box .dropdown--list").css("display", "block"); }
		else { $(".review-state--box .dropdown--list").css("display", "none"); }
	});

	// option 선택 시
	$(".review-state--box .dropdown--list li").click(function(){
		// list close
		$(".review-state--box .dropdown--list").css("display", "none");

		// '검토의뢰'와 선택한 항목만 보이지 않게하고 나머지는 보여줌
		$(".review-state--box .dropdown--list li").css("display", "block");
		$("#"+$(this).attr("id")).css("display", "none");
		$("#REQUEST_FOR_REVIEW").css("display", "none");

		// 선택한 항목을 화면에 보여줌
		$(".review-state--box .dropdown--select").text($(this).text());

		$("input[name=feedState]").val($(this).attr("id"));
	});

	// 저장 버튼
	$("#btnSave").click(function(){
		var feedStateValue 		= $("#reviewForm input[name=feedState]").val();
		var prevFeedStateValue 	= $("#reviewForm input[name=prevFeedState]").val();
		var reviewContents 		= $("#reviewForm textarea[name=reviewContents]");
		var fileLength 			= reviewFileUploader.files.length;
		var communityName 		= $("#reviewForm input[name=communityName]").val();

		// 상태 체크
		if(prevFeedStateValue == feedStateValue)
		{
			var stateValue 	= $('#'+feedStateValue).text();
			var stateResult = confirm('[ ' + stateValue + ' ]  상태를 유지하시겠습니까?');

			if(!stateResult) { return; }
		}

		// 수정요구, 반려, 검토완료의 경우 검토의견 글자수 체크
		if(feedStateValue == "REQUEST_FOR_CORRECTION" || feedStateValue == "REJECT" || feedStateValue == "REVIEW_COMPLETED")
		{
		 	if(reviewContents.val().length == 0)
		 	{
		 		alert("검토의견을 작성해주세요.");
		 		reviewContents.focus();
		 		return;
		 	}
		}

		// 검토완료의 경우 파일등록 체크
// 	 	if( (communityName == "계약서 검토" || communityName == "법률자문") && (feedStateValue == "REVIEW_COMPLETED" && fileLength == 0) )
// 	 	{
// 	 		alert("검토완료 파일을 첨부해주세요.");
// 	 		return;
// 	 	}

		// 삭제한 파일있으면 삭제 API를 호출하여준다.
		if(reviewFileUploader.deleteFiles)
		{
			$.each(reviewFileUploader.deleteFiles, function(i, url) {
				$.getJSON(url);
			});
		}

	 	// 최초 등록 시 파일을 업로드 하지 않은 경우
		if( (fileId.length == 0) || (fileId == null) || (fileId == undefined) ) { uploaderOptions.firstUploaded = true; }

		var files = reviewFileUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			reviewAfterAuthHandler();
		}
		else
		{
			reviewFileUploadCallback();
		}
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		var feedId 		= $("input[name=feedId]").val();
		var communityId = $("input[name=communityId]").val();
		location.href="/feed/detail?communityId="+communityId+"&feedId="+feedId;
	});
});
</script>

</common:page>