<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>
<style>
.excel--button
{
    position: relative;
    width: 75px;
    height: 40px;
    border: 1px solid #1cb103;
    background-color: #1cb103;
    color: #fff;
    line-height: 40px;
    font-size: 16px;
    font-weight: 600;
}
</style>

<h2 class="title">${communityInfo.communityName}</h2>
<section class="list">
    <div class="content">
        <!-- 검색 영역 -->
        <div class="search--box">
            <div class="dropdown--box search-term--box">
            	<input type="text" class="dropdown--select" name="daterangepicker" />
<!--                 <div class="dropdown--select">기간 입력</div> -->
                <div class="dropdown--list">
                    <!-- 캘린더 표시 -->
                </div>
            </div>
            <div class="dropdown--box search--select-box">
            	<input type="hidden" name="searchType" />
                <div class="dropdown--select">제목 + 내용</div>
                <ul class="dropdown--list">
                    <li id="TITLEANDCONTENTS">제목 + 내용</li>
                    <c:if test="${communityInfo.communityName eq '계약서 검토'}"><li id="CONTRACT_PARTNER">계약상대방</li></c:if>
                    <li id="ORGNAME">부서명</li>
                    <li id="USERNAME">작성자</li>
                </ul>
            </div>
            <div class="search--input-box">
                <input type="text" name="searchKeyword" placeholder="검색어를 입력하세요">
                <button type="button" id="btnSearchFeeds" class="search--button">검색</button>
                <c:if test="${userInfo.orgCode eq legalOrgCode}">
                &nbsp;&nbsp;&nbsp;<button type="button" id="btnSearchFeedsSaveExcel" class="excel--button">엑셀저장</button>
                </c:if>
            </div>
        </div>
        <!-- //검색 영역 -->

		<!-- 목록 영역 -->
		<div id="feedList">
        </div>
        <!-- //목록 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
        	<c:if test="${userInfo.orgCode ne legalOrgCode}">
            	<a href="/feed/writeForm?communityId=${communityId}" class="button primary-button">작성하기</a>
            </c:if>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

<script type="text/javascript">
function goPage(page)
{
	GLB_FILTER_PAGE = page;
	getFeeds('feedList');
}

function submitSearch()
{
	GLB_FILTER_PAGE 	= 1;
	GLB_SEARCH_KEYWORD 	= $("input[name=searchKeyword]").val();
	getFeeds('feedList');
}

function excelDownload(){
	GLB_FILTER_PAGE 	= 1;
	GLB_SEARCH_KEYWORD 	= $("input[name=searchKeyword]").val();
	
	var postData = {
		 'searchType' : GLB_SEARCH_TYPE
		,'page' : GLB_FILTER_PAGE
	};

	if(GLB_COMMUNITY_ID != null){
		postData.communityId = GLB_COMMUNITY_ID;
	}
	if(GLB_SEARCH_KEYWORD != null){
		postData.searchKeyword = GLB_SEARCH_KEYWORD;
	}
	if(GLB_FILTER_START_DT != null){
		postData.filterStartDt = GLB_FILTER_START_DT;
	}
	if(GLB_FILTER_END_DT != null){
		postData.filterEndDt = GLB_FILTER_END_DT;
	}

	var url = "/feed/list/excel";
	url += "?searchType=" + GLB_SEARCH_TYPE;
	url += "&page=" + GLB_FILTER_PAGE;
	url += "&communityId=" + GLB_COMMUNITY_ID;
	url += "&searchKeyword=" + GLB_SEARCH_KEYWORD;
	url += "&filterStartDt=" + GLB_FILTER_START_DT;
	url += "&filterEndDt=" + GLB_FILTER_END_DT;

	location.href = url;
	
}

function showFeedDetail(feedId, communityId)
{
	location.href="/feed/detail?"+"communityId="+communityId+"&feedId="+feedId;
}

$(document).ready(function(){
	GLB_COMMUNITY_ID 	= '${communityId}';
	GLB_FILTER_START_DT = '${startDate}';
	GLB_FILTER_END_DT 	= '${endDate}';
	getFeeds('feedList');

	$("#TITLEANDCONTENTS").css("display", "none");

	$(".search--select-box .dropdown--select").click(function(){
		var cssDisplayValue = $(".search--select-box .dropdown--list").css("display");

		if(cssDisplayValue == "none") { $(".search--select-box .dropdown--list").css("display", "block"); }
		else { $(".search--select-box .dropdown--list").css("display", "none"); }
	});

	// option 선택 시
	$(".search--select-box .dropdown--list li").click(function(){
		// list close
		$(".search--select-box .dropdown--list").css("display", "none");

		// 검색을 위해 GLB_SEARCH_TYPE에 선택한 항목의 id값을 넣음
		GLB_SEARCH_TYPE = $(this).attr("id");

		// 선택한 항목만 보이지 않게하고 나머지는 보여줌
		$(".search--select-box .dropdown--list li").css("display", "block");
		$("#"+$(this).attr("id")).css("display", "none");

		// 선택한 항목을 화면에 보여줌
		$(".search--select-box .dropdown--select").text($(this).text());
	});

	// 검색 버튼 클릭 시
	$("#btnSearchFeeds").click(function(){
		submitSearch();
	});
	
	$('#btnSearchFeedsSaveExcel').on('click',function(){
		excelDownload();
	});

	$("input[name=searchKeyword]").keyup(function(e){
		if(e.keyCode == "13") { submitSearch(); }
	});

	// 달력
	$('input[name="daterangepicker"]').keydown(function(e){
		e.preventDefault();
	});
	$('input[name="daterangepicker"]').daterangepicker({
		 opens: 'right'
		,startDate: '${startDate}'
		,endDate: '${endDate}'
		,drops: 'down'
		,locale: {
			 format: 'YYYY-MM-DD'
			,separator: " ~ "
			,cancelLabel: '취소'
			,applyLabel: '적용'
			,daysOfWeek: [ "일", "월", "화", "수", "목", "금", "토" ]
			,monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
		}
	}, function(start, end, label) {
		GLB_FILTER_START_DT = start.format('YYYY-MM-DD');
		GLB_FILTER_END_DT 	= end.format('YYYY-MM-DD');
	});

	setTimeout(function(){
		$('input[name="daterangepicker"]').data('daterangepicker').setStartDate('${startDate}');
		$('input[name="daterangepicker"]').data('daterangepicker').setEndDate('${endDate}');
	}, 100);
});
</script>

</common:page>