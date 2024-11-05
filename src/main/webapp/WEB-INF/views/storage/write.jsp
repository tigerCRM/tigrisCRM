<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<form id="storageWriteForm" action="/storage/create" method="POST">
<input type="hidden" name="fileId" />
<input type="hidden" name="userId" value="${userInfo.userId}" />

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
                        <td colspan="3"><input type="text" class="tb_input" name="title" placeholder="제목을 입력하세요"></td>
                    </tr>
                    <tr>
                        <th>부서명</th>
                        <td>${userInfo.orgName}</td>
                        <th>등록일</th>
                        <td>${createDate}</td>
                    </tr>
                    <tr>
                        <th>등록자</th>
                        <td colspan="3">${userInfo.userName}</td>
                    </tr>
                    <tr>
                        <th class="vt_top">내용</th>
                        <td colspan="3"><textarea spellcheck="false" class="tb_textarea" name="contents"></textarea></td>
                    </tr>
                    <tr>
                        <th class="vt_top">파일첨부</th>
                        <td colspan="3">
                            <!-- 파일 첨부 영역 추가 -->
                            <div id="storageFileUploader" style="display:block;">
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
            <button type="button" class="button primary-button" id="btnCreate">등록</button>
            <button type="button" class="button light-button" id="btnCancel">취소</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
var storageWriteUploader;

function fileUploadCallback() {
	var title 		= $("input[name=title]");
	var contents 	= $("textarea[name=contents]");
	var fileLength 	= storageWriteUploader.files.length;

	// 제목 글자수 체크
	if(title.val().length == 0) { alert("제목을 작성해주세요."); title.focus(); return; }
	if(checkWordByte(title.val(), 1000)) { alert("작성가능한 제목의 글자수가 초과하였습니다."); title.focus(); return; }

	// 내용 글자수 체크
	if(contents.val().length == 0) { alert("내용을 작성해주세요."); contents.focus(); return; }

	// 파일등록 체크 / 2023.07.10 -요청에의하여 첨부파일 필수여부 제외 
	//if(fileLength == 0) { alert("파일을 첨부해주세요."); return; }

	$("#storageWriteForm").submit();
}

function storageWriteAfterAuthHandler() {
	storageWriteUploader.start();
}

$(document).ready(function(){

	var firstUploaded = true; // fileId 생성을 위한 변수

	// 첨부 파일 옵션 셋팅
	var uploaderOptions = {
		'firstUploaded': firstUploaded,
		'uploaderId': 'storageFileUploader',
		'callback': fileUploadCallback,
		'authHandlerName': 'storageWriteAfterAuthHandler',
		'uploadedCallback': function (result) {
			$("input[name=fileId]").val(result.data);
			if(firstUploaded) { firstUploaded = false; }
			uploaderOptions.firstUploaded = firstUploaded;
		},
		'uploadError': function(args) {
		}
	};

	// 첨부 파일 모듈 로딩
	storageWriteUploader = loadPluploadQueue(uploaderOptions);

	// 등록 버튼
	$("#btnCreate").click(function(){
		var files = storageWriteUploader.files;
		if(files.length > 0)
		{
			// 첨부파일 업로드
			storageWriteAfterAuthHandler();
		}
		else
		{
			fileUploadCallback();
		}
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		location.href="/storage";
	});

});
</script>

</common:page>