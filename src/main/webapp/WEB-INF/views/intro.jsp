<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, maximum-scale=1, width=device-width">
    <title>씨젠의료재단 법무시스템</title>

    <link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/assets/css/intro.css" />

    <script src="/lib/jquery/jquery-2.2.2.min.js"></script>

    <script>
    	$(document).ready(function(){
    		var menuCount = $(".intro-menu--list li").size();
			// 메뉴 갯수가 4개 이하이면 가운데 정렬, 5개 이상이면 왼쪽으로 정렬
    		if(menuCount <= 4) { $(".intro-menu--list").css("justify-content", "center"); }
    		else { $(".intro-menu--list").css("justify-content", "flex-start"); }
    	});
    </script>
</head>
<body>
    <div id="skipMenu">
        <a href="#main">본문 바로가기</a>
    </div>
    <main id="main">
        <article id="intro">
            <h1 class="intro--logo"></h1>
            <h2>씨젠의료재단의 법무시스템을 소개합니다</h2>
            <section class="intro-menu">
                <div class="container">
                    <ul class="intro-menu--list">
						<li>
                            <a href="/storage">
                                <p class="menu--name">자료실</p>
                                <p class="menu--link">바로가기</p>
                            </a>
                        </li>
                    	<c:forEach items="${communityList}" var="community">
							<li>
	                            <a href="/feed?communityId=${community.communityId}">
	                                <p class="menu--name">${community.communityName}</p>
	                                <p class="menu--link">바로가기</p>
	                            </a>
	                        </li>
                    	</c:forEach>
                    </ul>
                </div>
            </section>
        </article>
    </main>
</body>
</html>