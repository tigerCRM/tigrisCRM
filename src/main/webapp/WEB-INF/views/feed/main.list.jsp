<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<input type="hidden" name="feedSearchDto" value="${feedSearchDto}" />
<input type="hidden" name="a" value="${feedSearchDto.filterStartDt}" />
<div class="list--table">
	<table class="tb_basic__list">
	    <colgroup>
	        <col style="width: 8%">
	        <col style="width: 40%">
           	<c:if test="${communityInfo.communityName eq '계약서 검토'}"><col style="width: 10%"></c:if>
	        <col style="width: 8%">
	        <col style="width: 10%">
	        <col style="width: 12%">
	        <col style="width: 12%">
	    </colgroup>
	    <thead>
	        <tr>
	            <th class="left">상태</th>
	            <c:choose>
	            	<c:when test="${communityInfo.communityName eq '계약서 검토'}"><th class="left">계약서명</th></c:when>
	            	<c:otherwise><th class="left">제목</th></c:otherwise>
	            </c:choose>
            	<c:if test="${communityInfo.communityName eq '계약서 검토'}"><th class="left">계약상대방</th></c:if>
	            <th class="left">부서명</th>
	            <th class="left">작성자</th>
	            <th class="left">검토의뢰일</th>
	            <th class="left">검토완료요청일</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${feedList}" var="feed">
		        <tr onClick="showFeedDetail('${feed.feedId}','${feedSearchDto.communityId}')">
		            <td class="left">${feed.feedStateName}</td>
		            <td class="board--title"><a href="#">${feed.title}</a></td>
            		<c:if test="${communityInfo.communityName eq '계약서 검토'}"><td class="left">${feed.contractPartner}</td></c:if>
		            <td class="left">${feed.orgName}</td>
		            <td class="left">${feed.userName} ${feed.jobgrade}</td>
		            <td class="left"><fmt:formatDate value='${feed.requestDate}' pattern='yyyy-MM-dd' /></td>
		            <td class="left"><fmt:formatDate value='${feed.reviewDate}' pattern='yyyy-MM-dd' /></td>
		        </tr>
	    	</c:forEach>
	    </tbody>
	</table>
</div>

<!-- 페이지네이션 -->
<div class="pagination" id="pagination">
    <ul>
    	<c:forEach items="${pages}" var="page">
	    	<c:choose>
	    		<c:when test="${page == '..'}"><li><a href="#">${page}</a></li></c:when>
	    		<c:otherwise>
	    			<c:choose>
		    			<c:when test="${page == feedSearchDto.page}">
		    				<li class="focus"><a href="#">${page}</a></li>
		    			</c:when>
		    			<c:otherwise>
		    				<li><a href="javascript:goPage(${page})">${page}</a></li>
		    			</c:otherwise>
	    			</c:choose>
	    		</c:otherwise>
	    	</c:choose>
        </c:forEach>
    </ul>
</div>
<!-- //페이지네이션 -->