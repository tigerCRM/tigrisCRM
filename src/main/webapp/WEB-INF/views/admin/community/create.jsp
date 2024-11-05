<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<common:page>

<input type="hidden" name="signatureYn" />

<h2 class="title">커뮤니티 등록</h2>
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
	            		<th>커뮤니티명</th>
                        <td colspan="3"><input type="text" class="tb_input" name="communityName" placeholder="커뮤니티명을 입력하세요"></td>
                    </tr>
                    <tr>
                        <th class="vt_top">글 양식</th>
                        <td colspan="3">
							<textarea spellcheck="false" class="tb_textarea" name="form"></textarea>
                        </td>
                    </tr>
                    <tr>
	            		<th>결재라인<br/>사용 여부</th>
                        <td colspan="3">
                        	<div class="dropdown--box review-state--box">
				                <div class="dropdown--select">사용</div>
				                <ul class="dropdown--list">
				                    <li id="Y">사용</li>
				                    <li id="N">미사용</li>
				                </ul>
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
        </div>
        <!-- //버튼 영역 -->
    </div>
</section>

<script>
function init()
{
	// 결재라인 사용 여부 값을 'Y'로 지정
	$("input[name=signatureYn]").val("Y");
	$(".dropdown--list li#Y").hide();
}

$(document).ready(function(){
	init();

	// 결재라인 사용 여부 select 클릭 시
	$(".review-state--box .dropdown--select").click(function(){
		var cssDisplayValue = $(".review-state--box .dropdown--list").css("display");

		if(cssDisplayValue == "none") { $(".review-state--box .dropdown--list").css("display", "block"); }
		else { $(".review-state--box .dropdown--list").css("display", "none"); }
	});

	// 결재라인 사용 여부 option 선택 시
	$(".review-state--box .dropdown--list li").click(function(){
		// list close
		$(".review-state--box .dropdown--list").css("display", "none");

		// 선택한 항목만 보이지 않게하고 나머지는 보여줌
		$(".review-state--box .dropdown--list li").css("display", "block");
		$("#"+$(this).attr("id")).css("display", "none");

		// 선택한 항목을 화면에 보여줌
		$(".review-state--box .dropdown--select").text($(this).text());
		$("input[name=signatureYn]").val($(this).attr("id"));
	});

	// 등록 버튼 클릭 시
	$("#btnCreate").click(function(){
		var communityName 	= $("input[name=communityName]").val();
		var form 			= $("textarea[name=form]").val();
		var signatureYn 	= $("input[name=signatureYn]").val();

		// 빈 값 체크
		if(communityName.length == 0) 	{ alert("커뮤니티명을 작성해주세요."); $("input[name=communityName]").focus(); }

		$.ajax({
			 url : "/admin/community/create"
			,data : {
				 communityName 	: communityName
				,form 			: form
				,signatureYn 	: signatureYn
			}
			,success : function(data){
				console.log(data);
				alert("커뮤니티가 등록되었습니다.");
			}
		});
	});
});
</script>

</common:page>