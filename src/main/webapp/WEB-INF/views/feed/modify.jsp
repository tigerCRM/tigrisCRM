<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<form id="feedModifyForm" action="/feed/update" method="POST">
<input type="hidden" name="feedId" 			value="${feed.feedId}" />
<input type="hidden" name="communityId" 	value="${communityId}" />
<input type="hidden" name="requestFileId" 	value="${feed.requestFileId}" />
<input type="hidden" name="requestDateStr" 	value="${feed.requestDateStr}" />
<input type="hidden" name="reviewDateStr" 	value="${feed.reviewDateStr}" />
<input type="hidden" name="feedState" 		value="${feed.feedState}" />
<input type="hidden" name="prevFeedState" 	value="${feed.feedState}" />
<input type="hidden" name="communityName" 	value="${communityInfo.communityName}" />

<h2 class="title">${communityInfo.communityName}</h2>
<section class="write">
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
                        <c:choose>
			            	<c:when test="${communityInfo.communityName eq '계약서 검토'}">
			            		<th>계약서명</th>
		                        <td colspan="3"><input type="text" class="tb_input" name="title" placeholder="계약서명을 입력하세요" value="${feed.title}"></td>
			            	</c:when>
			            	<c:otherwise>
			            		<th>제목</th>
		                        <td colspan="3"><input type="text" class="tb_input" name="title" placeholder="제목을 입력하세요" value="${feed.title}"></td>
			            	</c:otherwise>
			            </c:choose>
                    </tr>
                    <tr>
		            	<c:if test="${communityInfo.communityName eq '계약서 검토'}">
				            <th>계약상대방</th>
	                        <td colspan="3"><input type="text" class="tb_input" name="contractPartner" placeholder="계약상대방을 입력하세요" value="${feed.contractPartner}"></td>
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
                        <td>
                            <div class="dropdown--box request-date--box">
                                <input type="text" class="dropdown--select" name="datepicker" />
                                <div class="dropdown--list">
                                    <!-- 캘린더 표시 -->
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">검토요청내용<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3"><textarea spellcheck="false" class="tb_textarea" name="requestContents">${feed.requestContents}</textarea></td>
                    </tr>
                    <tr>
                        <th class="vt_top" style="border-bottom: 3px solid var(--cool-gray);">파일첨부<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3" style="border-bottom: 3px solid var(--cool-gray);">
                            <!-- 파일 첨부 영역 추가 -->
                            <div class="input_file">
	                            <div id="feedFileUploader" style="display:block;">
									<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
								</div>
							</div>
                        </td>
                    </tr>
                    <c:if test="${feed.feedState eq 'REQUEST_FOR_CORRECTION' || feed.feedState eq 'REJECT' || feed.feedState eq 'REVIEW_COMPLETED'}">
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
	                        		<p class="file--name"><a href="/file/download/${reviewFile.fileId}/${reviewFile.seq}">${reviewFile.originFileName} ${reviewFile.originFileName}</a></p>
	                        	</c:forEach>
	                        </td>
	                    </tr>
		            </c:if>
                </tbody>
            </table>
        </div>
        <!-- //글 쓰기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
        	<c:if test="${userInfo.userName eq feed.userName}">
        		<button type="button" class="button primary-button" id="btnModify">
	        		<c:choose>
			        	<c:when test="${feed.feedState ne 'WAITING_FOR_APPROVAL'}">
		            		검토요청
		            	</c:when>
			        	<c:otherwise>
		            		저장
		            	</c:otherwise>
	        		</c:choose>
	        	</button>
            </c:if>
            <button type="button" class="button light-button" id="btnCancel">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
var storyModifyUploader;

function fileUploadCallback() {
	$("#feedModifyForm").submit();
}

function storyModifyAfterAuthHandler() {
	storyModifyUploader.start();
}

$(document).ready(function(){
	var firstUploaded 	= false; // fileId 생성을 위한 값
	var fileId 			= '${feed.requestFileId}';

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'fileId': fileId,
		'uploaderId': 'feedFileUploader',
		'callback': fileUploadCallback,
		'authHandlerName': 'storyModifyAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=requestFileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	storyModifyUploader = loadPluploadQueue(uploaderOptions);

	// 수정 버튼
	$("#btnModify").click(function(){
		var communityName 	= $("input[name=communityName]").val();
		var title 			= $("input[name=title]").val();
		var contractPartner = $("input[name=contractPartner]").val();
		var requestContents = $("textarea[name=requestContents]").val();
		var fileLength 		= storyModifyUploader.files.length;
		
		// 부서장(팀장) 게시물 작성시 승인 안내문구 추가 2023.07.11
		const userId = "${userInfo.userId}";
		const teamLeaderId = "${userInfo.teamLeaderId}";

		// 제목 글자수 체크
		if(title.length == 0) { alert("제목을 작성해주세요."); $("input[name=title]").focus(); return; }
		if(checkWordByte(title, 1000)) { alert("작성가능한 제목의 글자수가 초과하였습니다."); $("input[name=title]").focus(); return; }

		// 계약상대방 글자수 체크
		if(communityName == "계약서 검토" && contractPartner.length == 0) { alert("계약상대방을 작성해주세요."); $("input[name=contractPartner]").focus(); return; }

		// 검토요청내용 글자수 체크
		if(requestContents.length == 0) { alert("검토요청내용을 작성해주세요."); $("textarea[name=requestContents]").focus(); return; }

		// 파일등록 체크
		// 2023.07.10 -요청에의하여 첨부파일 필수여부 제외 
		//if( (communityName == "계약서 검토" || communityName == "법률자문") && (fileLength == 0) ) { alert("검토요청 파일을 첨부해주세요."); return; }

		// 부서장(팀장) 게시물 작성시 승인 안내문구 추가 2023.07.11
		if( userId == teamLeaderId ) {
			if(!confirm("부서장(팀장)이 게시물을 등록하여도 반드시 게시물의 승인 처리를 해야 합니다.")){ return; };
		}
		
		// 삭제한 파일있으면 삭제 API를 호출하여준다.
		if(storyModifyUploader.deleteFiles)
		{
			$.each(storyModifyUploader.deleteFiles, function(i, url) {
				$.getJSON(url);
			});
		}

		// 최초 등록 시 파일을 업로드 하지 않은 경우
		if( (fileId.length == 0) || (fileId == null) || (fileId == undefined) ) { uploaderOptions.firstUploaded = true; }

		var files = storyModifyUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			storyModifyAfterAuthHandler();
		}
		else
		{
			fileUploadCallback();
		}
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		var feedId 		= $("input[name=feedId]").val();
		var communityId = $("input[name=communityId]").val();
		location.href="/feed/detail?communityId="+communityId+"&feedId="+feedId;
	});

	// 달력
	$('input[name="datepicker"]').keydown(function(e){
		e.preventDefault();
	});
	$('input[name="datepicker"]').daterangepicker({
		 opens: 'right'
		,singleDatePicker: true
		,autoApply: true
		,drops: 'down'
		,startDate: '${feed.reviewDateStr}'
// 		,minDate: new Date()
		,locale: {
			 format: 'YYYY-MM-DD'
			,cancelLabel: '취소'
			,applyLabel: '적용'
			,daysOfWeek: [ "일", "월", "화", "수", "목", "금", "토" ]
			,monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
		}
	}, function(start, end, label) {
		$("input[name=reviewDateStr]").val(start.format('YYYY-MM-DD'));
	});
});
</script>

</common:page>