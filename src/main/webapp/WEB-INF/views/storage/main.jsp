<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<h2 class="title">자료실</h2>
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
                    <li id="ORGNAME">부서명</li>
                    <li id="USERNAME">작성자</li>
                </ul>
            </div>
            <div class="search--input-box">
                <input type="text" name="searchKeyword" placeholder="검색어를 입력하세요">
                <button class="search--button">검색</button>
            </div>
        </div>
        <!-- //검색 영역 -->

		<!-- 목록 영역 -->
		<div id="storageList">
        </div>
        <!-- //목록 영역 -->

        <!-- 버튼 영역 -->
        <div class="button--box">
            <a href="/storage/writeForm" class="button primary-button">작성하기</a>
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

<script type="text/javascript">
function goPage(page)
{
	GLB_FILTER_PAGE = page;
	getStorageList('storageList');
}

function submitSearch()
{
	GLB_FILTER_PAGE 	= 1;
	GLB_SEARCH_KEYWORD 	= $("input[name=searchKeyword]").val();
	getStorageList('storageList');
}

function showStorageDetail(storageId)
{
	location.href="/storage/detail?storageId="+storageId;
}

$(document).ready(function(){
	GLB_FILTER_START_DT = '${startDate}';
	GLB_FILTER_END_DT 	= '${endDate}';
	getStorageList('storageList');

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
	$(".search--button").click(function(){
		submitSearch();
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