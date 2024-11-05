<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<style>
#chiefStaffNm { cursor:pointer; }
</style>

<form id="feedWriteForm" action="/feed/create" method="POST">
<input type="hidden" name="requestFileId" />
<input type="hidden" name="communityId" 	value="${communityId}" />
<input type="hidden" name="userId" 			value="${userInfo.userId}" />
<input type="hidden" name="requestDateStr" 	value="${dateMap.requestDate}" />
<input type="hidden" name="reviewDateStr" 	value="${dateMap.reviewDate}" />
<input type="hidden" name="communityName" 	value="${communityInfo.communityName}" />
<input type="hidden" name="chiefStaffNm_" 	value="${userInfo.chiefStaffNm}" />
<input type="hidden" name="teamLeaderId" />

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
	                    <td>${userInfo.userName}</td>
	                    <td id="chiefStaffNm" onclick="javascript:showOrganizationChart();">${userInfo.chiefStaffNm}</td>
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
		                        <td colspan="3"><input type="text" class="tb_input" name="title" placeholder="계약서명을 입력하세요"></td>
			            	</c:when>
			            	<c:otherwise>
			            		<th>제목</th>
		                        <td colspan="3"><input type="text" class="tb_input" name="title" placeholder="제목을 입력하세요"></td>
			            	</c:otherwise>
			            </c:choose>
                    </tr>
                    <tr>
		            	<c:if test="${communityInfo.communityName eq '계약서 검토'}">
				            <th>계약상대방</th>
	                        <td colspan="3"><input type="text" class="tb_input" name="contractPartner" placeholder="계약상대방을 입력하세요"></td>
		            	</c:if>
                    </tr>
                    <tr>
                        <th>부서명</th>
                        <td>${userInfo.orgName}</td>
                        <th>검토의뢰일</th>
                        <td>${dateMap.requestDate}</td>
                    </tr>
                    <tr>
                        <th>등록자</th>
                        <td>${userInfo.userName}</td>
                        <th>검토완료요청일</th>
                        <td>
                            <div class="dropdown--box request-date--box">
<%--                                 <div class="dropdown--select">${dateMap.reviewDate}</div> --%>
								<input type="text" class="dropdown--select" name="datepicker" />
                                <div class="dropdown--list">
                                    <!-- 캘린더 표시 -->
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">검토요청내용<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3">
                        <!-- 20221005 게시글 양식 적용 -->
<%--                         	<c:choose> --%>
<%--                         		<c:when test="${communityInfo.communityName eq '계약서 검토'}"> --%>
<!-- 									<textarea spellcheck="false" class="tb_textarea" name="requestContents">- 계약기간 : &#10;&#10;- 계약금액 : &#10;&#10;- 계약서에 반영되어야 할 내용 : &#10;&#10;- 계약쟁점사항 : &#10;&#10;- 그 외 검토 요청사항 : </textarea> -->
<%--                         		</c:when> --%>
<%--                         		<c:when test="${communityInfo.communityName eq '법률자문'}"> --%>
<!-- 									<textarea spellcheck="false" class="tb_textarea" name="requestContents">6하원칙에 따라 작성해주세요.</textarea> -->
<%--                         		</c:when> --%>
<%-- 			            		<c:otherwise><textarea spellcheck="false" class="tb_textarea" name="requestContents"></textarea></c:otherwise> --%>
<%--                         	</c:choose> --%>

						<!-- 20230712 텍스트영역에 표시되었던 불필요한 내용을 placeholder로 변경 -->
							<c:choose>
								<c:when test="${communityInfo.communityName eq '계약서 검토'}">
									<textarea spellcheck="false" class="tb_textarea" name="requestContents">${communityInfo.form}</textarea>
								</c:when>
								<c:otherwise>
									<textarea spellcheck="false" class="tb_textarea" name="requestContents" placeholder="${communityInfo.form}"></textarea>
								</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">파일첨부<br><span class="desc">(등록자 작성)</span></th>
                        <td colspan="3">
                            <!-- 파일 첨부 영역 추가 -->
                            <div id="feedFileUploader" style="display:block;">
								<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
							</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- //글 쓰기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
            <button type="button" class="button primary-button" id="btnCreate">검토요청</button>
            <button type="button" class="button light-button" id="btnCancel">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
var storyWriteUploader;

function fileUploadCallback() {
	$("#feedWriteForm").submit();
}

function storyWriteAfterAuthHandler() {
	storyWriteUploader.start();
}

function showOrganizationChart() {
	var positionX = window.screen.width / 2 - 250;
	var positionY = window.screen.height / 2 - 269;
	window.open('/common/popOrganizationTree', 'test', 'width=500, height=538, top='+positionY+', left='+positionX);
}

$(document).ready(function(){

	var firstUploaded = true; // fileId 생성을 위한 변수

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'uploaderId': 'feedFileUploader',
		'callback': fileUploadCallback,
		'authHandlerName': 'storyWriteAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=requestFileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	storyWriteUploader = loadPluploadQueue(uploaderOptions);

	// 검토요청 버튼
	$("#btnCreate").click(function(){
		var chiefStaffNm_ 	= $("input[name=chiefStaffNm_]").val();
		var communityName 	= $("input[name=communityName]").val();
		var title 			= $("input[name=title]").val();
		var contractPartner = $("input[name=contractPartner]").val();
		var requestContents = $("textarea[name=requestContents]").val();
		var fileLength 		= storyWriteUploader.files.length;
		
		// 부서장(팀장) 게시물 작성시 승인 안내문구 추가 2023.07.11
		const userId = $("input[name=userId]").val();
		const teamLeaderId = "${userInfo.teamLeaderId}";

		// 팀장의 정보가 올바드지 않은 경우 알림창을 띄움.
		if( (chiefStaffNm_ == "null") || (chiefStaffNm_ == null) || (chiefStaffNm_ == "NONE") )
		{
			alert("결재라인의 팀장 정보가 올바르지 않습니다.\n관리자에게 문의해주세요.");
			return;
		}

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

		var files = storyWriteUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			storyWriteAfterAuthHandler();
		}
		else
		{
			fileUploadCallback();
		}
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		var communityId = $("input[name=communityId]").val();
		location.href="/feed?communityId="+communityId;
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
		,startDate: '${dateMap.reviewDate}'
		,minDate: '${dateMap.requestDate}'
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