<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/assets/css/default.css" />
    <link rel="stylesheet" href="/assets/css/tigris-common.css" />
	<script src="/lib/jquery/jquery-2.2.2.min.js"></script>
	<script src="/lib/jquery/plugins/jquery-ui.min.js"></script>
	<script src="/lib/jquery/plugins/jquery.slimscroll.min.js"></script>
	<script src="/assets/js/ui_common.js"></script>

    <!-- dynatree -->
    <link href="/lib/jquery/plugins/dynatree/skin-vista/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet">
    <script src="/lib/jquery/plugins/dynatree/jquery.dynatree.js" type="text/javascript"></script>

	<style type="text/css">
	ul.dynatree-container {
	    border: 1px solid #d2d2d2;
	}
	ul.dynatree-container a {
	    color: #666;
	    font-weight: 100;
	}
	</style>
</head>
<body>
    <div class="lay_pop pop_organization_chart" id="popOrganizationChart" >
		<div class="pop_tit">
			<span>조직도</span>
			<button type="button" class="close" onClick="self.close();"></button>
		</div>

		<div  class="pop_con">

			<div class="div_search">
				<input type="text" id="searchKeyword" placeholder="이름을 검색하세요." />
				<button type="button" class="org_button btn_b" style="background-color:#b60f0f;" ><span>검색</span></button>
			</div>

			<div class="div_organization">
				<div class="org_list" id="organizationDiv" >
				</div>

				<div class="person_list" id="personDiv">
					<ul>
					</ul>
				</div>
			</div>
		</div>
    </div>

	<script>
	$(document).ready(function(){

		$("#popOrganizationChart #organizationDiv").dynatree({
			initAjax: {
				url: "/common/getOrgTree",
				data: {upOrgId : ""}
			},
			onActivate: function(node) {
				getOrgPersonList(node.data.orgCode, "ORGCODE");
			},
		    onPostInit: function (isReloading, isError) {
	            $("#popOrganizationChart #organizationDiv").dynatree("getRoot").visit(function(node){
	                node.expand(true);
	            });

	         	// 나의 조직이 처음 선택되도록
				var node = $("#organizationDiv").dynatree("getTree").getNodeByKey('<c:out value="${orgCode}" />');
				if(node != null){
					node.activate(true);
				}
		    }
		});

		$("#popOrganizationChart #organizationDiv").dynatree("getRoot").visit(function(node){
		    node.expand(true);
		});

		//검색 리스트 스크롤
		$('#popOrganizationChart #personDiv ul').slimscroll({
			height: '427px',
			size: '6px',
			color: '#48414A',
			alwaysVisible: true,
			distance: '5px'
		});

		//검색
		$('#popOrganizationChart #searchKeyword').keydown(function( event ) {
			if (event.keyCode === 13 ) {
				var keyword = $(this).val();
				getOrgPersonList('SEARCH','SEARCH',keyword);
			}
		});
		$('#popOrganizationChart div.div_search button').on('click',function(){
			var keyword = $('#popOrganizationChart #searchKeyword').val();
			getOrgPersonList('SEARCH','SEARCH',keyword);
		});
	});

	/**
	 * 조직원 리스트 가져오기
	 */
	function getOrgPersonList(orgCode, searchType, keyword) {

		$.ajax({
			type: 'GET',
			url: '/common/getOrgPersonlist',
			data: {'orgCode' : orgCode, 'searchType' : searchType, 'keyword' : keyword},
			dataType: 'html',
			success: function(data, textStatus, XMLHttpRequest) {
				$('#popOrganizationChart #personDiv ul').html(data);
			}
		});
	}


	function closeOrgChart() {
		$( ".dimm" ).fadeTo( "fast" , 0, function() {
			dimm_control("out")

			$('#popOrganizationChart').hide();
			$('#popOrganizationChart').empty();
		});
	}
	</script>
</body>
</html>