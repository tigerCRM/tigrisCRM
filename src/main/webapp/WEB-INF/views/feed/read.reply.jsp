<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<input type="hidden" name="replyFileId" />
<input type="hidden" name="layerId" value="${layerId}" />

<div class="comment--title">댓글</div>
<div class="comment--list">
	<ul>
		<c:forEach items="${replyList}" var="reply">
         <li class="comment--item" id="reply_${reply.feedId}">
             <div class="comment-item--header">
                 <div class="comment-item--info">
                     <span class="com-name">${reply.userName} ${reply.jobgrade}</span>
                     <span class="com-date">${reply.updateDtStr}</span>
                 </div>
                 <c:if test="${userInfo.userId eq reply.userId}">
                  <div class="comment-item--util">
                      <button type="button" onclick="modifyReply('${layerId}', '${reply.feedId}', '${reply.upFeedId}')" class="icon-button modify-button" title="수정"><span class="hide">수정</span></button>
                      <button type="button" onclick="deleteReplySubmit('${layerId}', '${reply.feedId}', '${reply.upFeedId}')" class="icon-button delete-button" title="삭제"><span class="hide">삭제</span></button>
                  </div>
                 </c:if>
             </div>
             <!-- 댓글 내용 -->
             <div class="comment-item--content">
                 <p style="margin-bottom:10px;line-height:15px;white-space:break-spaces;">${reply.replyContents}</p>
                 <c:forEach items="${reply.replyFileList}" var="replyFile">
	                 <p class="file--name" style="margin-left:10px;"><a href="/file/download/${replyFile.fileId}/${replyFile.seq}">${replyFile.originFileName}</a></p>
                 </c:forEach>
             </div>
             <!-- //댓글 내용 -->
         </li>
		</c:forEach>
    </ul>
</div>
<div class="comment-write--box comment--no-print">
    <div class="comment-write">
        <textarea spellcheck="false" class="com-input" placeholder="댓글을 남겨보세요." name="replyContents"></textarea>
        <div class="comment-write--util">
            <button type="button" class="file-button" title="파일 첨부" id="btnUploadReplyFile"><span class="hide">파일 첨부</span></button>
            <button type="button" class="button primary-button" id="btnCreateReply">등록</button>
        </div>
    </div>
    <!-- 파일 첨부 영역 추가 -->
    <div id="replyWriteUploader" style="display:none;margin-top:10px;">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
</div>

<script type="text/javascript">
var replyWriteUploader;

function replyWriteUploaderCallback() {
	var communityId 	= $("input[name=communityId]").val();
	var feedId 			= $("input[name=feedId]").val();
	var replyContents 	= $("textarea[name=replyContents]").val();
	var replyFileId 	= $("input[name=replyFileId]").val();
	var layerId 		= $("input[name=layerId]").val();

	$.ajax({
		 url: "/feed/reply/create"
		,type: "POST"
		,data: {
			 communityId : communityId
			,feedId : feedId
			,replyContents : replyContents
			,replyFileId : replyFileId
		}
		,success: function(data) {
			getReplyList(layerId, feedId);
		}
	});
}

function replyWriteAfterAuthHandler() {
	replyWriteUploader.start();
}

// 댓글 수정 버튼
function modifyReply(layerId, feedId, upFeedId)
{
	$.ajax({
		 url: "/feed/reply/modifyReply"
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

// 프린트 영역 설정
function setPrintArea()
{
	// 댓글이 없으면 프린트 영역에서 해제
	if( $(".comment--list ul li").size() == 0 )
	{
		$(".comment--title").addClass("comment--no-print");
		$(".comment--list").addClass("comment--no-print");
	}
}

$(document).ready(function(){
	setPrintArea(); // 프린트 영역 설정

	var firstUploaded = true; // fileId 생성을 위한 변수

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'uploaderId': 'replyWriteUploader',
		'callback': replyWriteUploaderCallback,
		'authHandlerName': 'replyWriteAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=replyFileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	replyWriteUploader = loadPluploadQueue(uploaderOptions);

	// 댓글 파일 버튼
	$("#btnUploadReplyFile").click(function(){
		var displayValue = $("#replyWriteUploader").css("display");

		if( displayValue == "none" ) { $("#replyWriteUploader").css("display", "block"); }
		else { $("#replyWriteUploader").css("display", "none"); }
	});

	// 댓글 등록 버튼
	$("#btnCreateReply").click(function(){
		// 댓글 내용 체크
		var replyContents = $("textarea[name=replyContents]");
		if(replyContents.val().length == 0) { alert("댓글 내용을 작성해주세요."); replyContents.focus(); return; }

		// 첨부파일 체크
		var files = replyWriteUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			replyWriteAfterAuthHandler();
		}
		else
		{
			replyWriteUploaderCallback();
		}
	});
});
</script>