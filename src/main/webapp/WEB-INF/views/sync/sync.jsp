<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
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
</head>
<body>
    <div id="skipMenu">
        <a href="#main">본문 바로가기</a>
    </div>
    <main id="main">
        <article id="intro">
            <h1 class="intro--logo"></h1>

            <div class="button--box" style="text-align:center; margin-top:50px;">
	            <button type="button" class="button primary-button" id="btnStart">동기화 시작</button>
	        </div>

            <h2 id="message"></h2>
            <div style="width:200px;margin:0 auto;" id="info">
            	<h4 style="margin:15px 0 5px 0;">조직 동기화 정보</h4>
            	<p id="insertOrgCnt"></p>
            	<p id="updateOrgCnt"></p>
            	<p id="deleteOrgCnt"></p>
            	<h4 style="margin:15px 0 5px 0;">사용자 동기화 정보</h4>
            	<p id="insertUserCnt"></p>
            	<p id="updateUserCnt"></p>
            	<p id="deleteUserCnt"></p>
            </div>
        </article>
    </main>
</body>

<script type="text/javascript">
function syncStart()
{
	$("#btnStart").hide();
	$("#message").text("조직 및 사용자 동기화 중입니다.");
	$("#info").hide();

	$.ajax({
		 url: "/sync/execute"
		,type: "GET"
		,success: function(data) {
			if(data.code == 0)
			{
				var orgResultObj 	= data.data.orgResultObj;
				var userResultObj 	= data.data.userResultObj;
				$("#insertOrgCnt").text("신규 : " + orgResultObj.insertOrgCnt);
				$("#updateOrgCnt").text("기존 : " + orgResultObj.updateOrgCnt);
				$("#deleteOrgCnt").text("삭제 : " + orgResultObj.deleteOrgCnt);
				$("#insertUserCnt").text("신규 : " + userResultObj.insertUserCnt);
				$("#updateUserCnt").text("기존 : " + userResultObj.updateUserCnt);
				$("#deleteUserCnt").text("삭제 : " + userResultObj.deleteUserCnt);

				$("#message").text("조직 및 사용자 동기화가 완료되었습니다.");
				$("#info").show();
			}
			else
			{
				$("#message").text(data.message);
			}
		}
		,complete: function(){
			$("#btnStart").show();
		}
	});
}

$(document).ready(function(){
	$("#info").hide();
// 	syncStart();

	// 동기화 시작 버튼
	$("#btnStart").click(function(){
		syncStart();
	});
});
</script>
</html>