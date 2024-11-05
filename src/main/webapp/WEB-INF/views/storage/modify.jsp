<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<form id="storageModifyForm" action="/storage/update" method="POST">
<input type="hidden" name="storageId" 	value="${storage.storageId}" />
<input type="hidden" name="fileId" 		value="${storage.fileId}" />

<h2 class="title">자료실</h2>
<section class="write">

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
                        <td colspan="3"><input type="text" class="tb_input" name="title" placeholder="제목을 입력하세요" value="${storage.title}"></td>
                    </tr>
                    <tr>
                        <th>부서명</th>
                        <td>${storage.orgName}</td>
                        <th>등록일</th>
                        <td>${storage.createDtStr}</td>
                    </tr>
                    <tr>
                        <th>등록자</th>
                        <td colspan="3">${storage.userName}</td>
                    </tr>
                    <tr>
                        <th class="vt_top">내용</th>
                        <td colspan="3"><textarea spellcheck="false" class="tb_textarea" name="contents">${storage.contents}</textarea></td>
                    </tr>
                    <tr>
                        <th class="vt_top">파일첨부</th>
                        <td colspan="3">
                            <!-- 파일 첨부 영역 추가 -->
                            <div class="input_file">
	                            <div id="storageFileUploader" style="display:block;">
									<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
								</div>
							</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- //글 쓰기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
       		<button type="button" class="button primary-button" id="btnModify">저장</button>
            <button type="button" class="button light-button" id="btnCancel">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
var storageModifyUploader;

function fileUploadCallback() {
	$("#storageModifyForm").submit();
}

function storageModifyAfterAuthHandler() {
	storageModifyUploader.start();
}

$(document).ready(function(){
	var firstUploaded 	= false; // fileId 생성을 위한 값
	var fileId 			= '${storage.fileId}';

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'fileId': fileId,
		'uploaderId': 'storageFileUploader',
		'callback': fileUploadCallback,
		'authHandlerName': 'storageModifyAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=fileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	storageModifyUploader = loadPluploadQueue(uploaderOptions);

	// 수정 버튼
	$("#btnModify").click(function(){
		var title 		= $("input[name=title]");
		var contents 	= $("textarea[name=contents]");
		var fileLength 	= storageModifyUploader.files.length;

		// 제목 글자수 체크
		if(title.val().length == 0) { alert("제목을 작성해주세요."); title.focus(); return; }
		if(checkWordByte(title.val(), 1000)) { alert("작성가능한 제목의 글자수가 초과하였습니다."); title.focus(); return; }

		// 내용 글자수 체크
		if(contents.val().length == 0) { alert("내용을 작성해주세요."); contents.focus(); return; }

		// 파일등록 체크
		if(fileLength == 0) { alert("파일을 첨부해주세요."); return; }

		// 삭제한 파일있으면 삭제 API를 호출하여준다.
		if(storageModifyUploader.deleteFiles)
		{
			$.each(storageModifyUploader.deleteFiles, function(i, url) {
				$.getJSON(url);
			});
		}

		// 최초 등록 시 파일을 업로드 하지 않은 경우
		if( (fileId.length == 0) || (fileId == null) || (fileId == undefined) ) { uploaderOptions.firstUploaded = true; }

		var files = storageModifyUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			storageModifyAfterAuthHandler();
		}
		else
		{
			fileUploadCallback();
		}
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		var storageId 	= $("input[name=storageId]").val();
		location.href="/storage/detail?storageId="+storageId;
	});
});
</script>

</common:page>