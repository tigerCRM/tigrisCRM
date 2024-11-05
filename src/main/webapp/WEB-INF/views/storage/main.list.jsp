<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<div class="list--table">
	<table class="tb_basic__list">
	    <colgroup>
	        <col style="width: 50%">
	        <col style="width: 15%">
	        <col style="width: 15%">
	        <col style="width: 20%">
	    </colgroup>
	    <thead>
	        <tr>
	            <th class="left">제목</th>
	            <th class="left">부서명</th>
	            <th class="left">작성자</th>
	            <th class="left">작성일</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${storageList}" var="storage">
		        <tr onClick="showStorageDetail('${storage.storageId}')">
		            <td class="board--title"><a href="#">${storage.title}</a></td>
		            <td class="left">${storage.orgName}</td>
		            <td class="left">${storage.userName} ${storage.jobGrade}</td>
		            <td class="left"><fmt:formatDate value='${storage.createDt}' pattern='yyyy-MM-dd' /></td>
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