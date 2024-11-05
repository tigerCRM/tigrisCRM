<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<style>
#btnDelete { float:left; }
</style>

<form id="storageReadForm" action="/storage/modify" method="GET">
<input type="hidden" name="storageId" value="${storage.storageId}" />

<h2 class="title">자료실</h2>
<section class="view">

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
                        <td colspan="3" class="board--title">${storage.title}</td>
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
                        <td colspan="3">
                            <p style="white-space:break-spaces;">${storage.contents}</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="vt_top">파일첨부</th>
                        <td colspan="3">
                        	<c:forEach items="${fileList}" var="file">
                        		<p class="file--name"><a href="/file/download/${file.fileId}/${file.seq}">${file.originFileName} <small style="color:var(--cool-gray-regular)">(${file.createDtStr})</small></a></p>
                        	</c:forEach>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- //글 보기 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
        	<!-- 등록자 -->
       		<c:if test="${userInfo.userId eq storage.userId}">
            	<button type="button" class="button default-button" id="btnDelete">삭제</button>
            	<button type="submit" class="button primary-button">수정</button>
       		</c:if>

            <button type="button" id="btnCancel" class="button light-button">목록으로</button>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

</form>

<script type="text/javascript">
$(document).ready(function(){
	// 목록으로 버튼
	$("#btnCancel").click(function(){
		location.href="/storage";
	});

	// 삭제 버튼
	$("#btnDelete").click(function(){
		var storageId = $("input[name=storageId]").val();

		$.ajax({
			 url : "/storage/delete"
			,data : {
				storageId : storageId
			}
			,success : function(data){
				if(data.code == "0")
				{
					alert("게시글이 삭제되었습니다.");
					location.href="/storage";
				}
			}
		});
	});
});
</script>

</common:page>