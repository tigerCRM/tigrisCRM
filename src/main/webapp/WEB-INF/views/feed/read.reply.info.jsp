<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="comment-item--header">
    <div class="comment-item--info">
        <span class="com-name">${replyInfo.userName} ${replyInfo.jobgrade}</span>
        <span class="com-date">${replyInfo.updateDtStr}</span>
    </div>
    <c:if test="${userInfo.userId eq replyInfo.userId}">
     <div class="comment-item--util">
         <button type="button" onclick="modifyReply('${layerId}', '${replyInfo.feedId}', '${replyInfo.upFeedId}')" class="icon-button modify-button" title="수정"><span class="hide">수정</span></button>
         <button type="button" onclick="deleteReplySubmit('${layerId}', '${replyInfo.feedId}', '${replyInfo.upFeedId}')" class="icon-button delete-button" title="삭제"><span class="hide">삭제</span></button>
     </div>
    </c:if>
</div>
<!-- 댓글 내용 -->
<div class="comment-item--content">
    <p style="margin-bottom:10px;line-height:15px;white-space:break-spaces;">${replyInfo.replyContents}</p>
    <c:forEach items="${replyInfo.replyFileList}" var="replyFile">
    	<p class="file--name" style="margin-left:10px;"><a href="/file/download/${replyFile.fileId}/${replyFile.seq}">${replyFile.originFileName}</a></p>
    </c:forEach>
</div>
<!-- //댓글 내용 -->