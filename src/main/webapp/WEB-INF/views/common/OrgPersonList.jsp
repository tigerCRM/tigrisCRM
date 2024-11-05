<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:forEach var="orgPersonList" items="${orgPersonList}" varStatus="status">
	<li style="padding-left:0px;" data-id="<c:out value="${orgPersonList.userId}" />" data-name="<c:out value="${orgPersonList.userName}" />">
		<button type="button" class="wrap_user" style="width:100%;text-align: left;  padding-left: 13px;">
			<div class="user_info">
				<c:out value="${orgPersonList.userName}" /><span><c:out value="${orgPersonList.orgName}" />/<c:out value="${orgPersonList.jobGrade}" /></span>
			</div>
		</button>
	</li>
</c:forEach>




<script>
$(document).ready(function(){
	$('button.wrap_user').click(function(){
		var chiefStaffId = $(this).closest('li').attr('data-id');
		var chiefStaffNm = $(this).closest('li').attr('data-name');

		window.opener.document.getElementsByName("teamLeaderId")[0].value = chiefStaffId;
		window.opener.document.getElementById("chiefStaffNm").innerHTML = chiefStaffNm;

		window.close();
	});
});
</script>