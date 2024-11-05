<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<nav id="lnb">
    <div class="lnb--title">
        <span>법무시스템</span>
    </div>
    <ul class="lnb--1depth">

    	<c:set var="URI" value="${requestScope['javax.servlet.forward.servlet_path']}" />
		<li <c:if test="${fn:contains(URI, '/storage')}"> class="focus" </c:if> ><a href="/storage">자료실</a></li>

    	<c:forEach items="${communityList}" var="community">
    		<c:choose>
    			<c:when test="${communityId eq community.communityId}">
    				<li class="focus"><a href="/feed?communityId=${community.communityId}">${community.communityName}</a></li>
    			</c:when>
    			<c:otherwise>
    				<li><a href="/feed?communityId=${community.communityId}">${community.communityName}</a></li>
    			</c:otherwise>
    		</c:choose>
    	</c:forEach>
    </ul>
</nav>