<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="replyModifyForm" action="#" method="POST">
	<input type="hidden" name="feedId" 		value="${replyInfo.feedId}" />
	<input type="hidden" name="replyFileId" value="${replyInfo.replyFileId}" />
	<input type="hidden" name="layerId" 	value="${layerId}" />

	<div class="comment-item--header">
	    <div class="comment-item--info">
	        <span class="com-name">${replyInfo.userName} ${replyInfo.jobgrade}</span>
	    </div>
	    <div class="comment-item--util">
	        <button type="button" onclick="replyModifyCancel('${replyInfo.feedId}', '${layerId}')" class="icon-button modify-button" title="수정취소"><span class="hide">수정취소</span></button>
	        <button type="button" onclick="deleteReplySubmit('${layerId}', '${replyInfo.feedId}', '${replyInfo.upFeedId}')" class="icon-button delete-button" title="삭제"><span class="hide">삭제</span></button>
	    </div>
	</div>
	<!-- 댓글 수정 -->
	<div class="comment-item--content comment-item--modify">
	    <div class="comment-write">
	        <textarea spellcheck="false" class="com-input" name="replyContents">${replyInfo.replyContents}</textarea>
	        <div class="comment-write--util">
				<div></div>
	            <button type="button" id="btnModifyReply" class="button primary-button">등록</button>
	        </div>
	    </div>
	</div>
	<!-- //댓글 수정 -->
	<!-- 파일 첨부 영역 추가 -->
	<div id="replyModifyUploader" style="display:block;margin-top:10px;">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
</form>

<script type="text/javascript">
var replyModifyUploader;

function replyModifyUploaderCallback() {
	var feedId 		= '${replyInfo.feedId}';
	var formData 	= new FormData($('#replyModifyForm')[0]);
	$.ajax({
		 url: "/feed/reply/update"
		,type: "POST"
		,dataType: "html"
		,data: formData
		,cache: false
		,contentType: false
		,processData: false
		,success: function(data) {
			$("#reply_"+feedId).empty();
			$("#reply_"+feedId).append(data);
		}
	});
}

function replyModifyAfterAuthHandler() {
	replyModifyUploader.start();
}

// 수정취소 버튼
function replyModifyCancel(feedId, layerId)
{
	$.ajax({
		 url: "/feed/reply/replyInfo"
		,type: "POST"
		,dataType: "html"
		,data: {
			 feedId : feedId
			,layerId : layerId
		}
		,success: function(data) {
			$("#reply_"+feedId).empty();
			$("#reply_"+feedId).append(data);
		}
	});
}

$(document).ready(function(){
	var firstUploaded 	= false; // fileId 생성을 위한 변수
	var fileId 			= '${replyInfo.replyFileId}';

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'fileId': fileId,
		'uploaderId': 'replyModifyUploader',
		'callback': replyModifyUploaderCallback,
		'authHandlerName': 'replyModifyAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=replyFileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	replyModifyUploader = loadPluploadQueue(uploaderOptions);

	// 등록 버튼
	$("#btnModifyReply").click(function(){
		// 댓글 내용 체크
		var replyContents = $("#replyModifyForm").find("textarea[name=replyContents]");
		if(replyContents.val().length == 0) { alert("댓글 내용을 작성해주세요."); replyContents.focus(); return; }

		// 삭제한 파일있으면 삭제 API를 호출하여준다.
		if(replyModifyUploader.deleteFiles)
		{
			$.each(replyModifyUploader.deleteFiles, function(i, url) {
				$.getJSON(url);
			});
		}

		// 최초 등록 시 파일을 업로드 하지 않은 경우
		if( (fileId.length == 0) || (fileId == null) || (fileId == undefined) ) { uploaderOptions.firstUploaded = true; }

		var files = replyModifyUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			replyModifyAfterAuthHandler();
		}
		else
		{
			replyModifyUploaderCallback();
		}
	});
});
</script>