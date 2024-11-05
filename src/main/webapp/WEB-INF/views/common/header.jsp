<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, maximum-scale=1, width=device-width">
    <title>씨젠의료재단 법무시스템</title>

    <link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/assets/css/sub.css" />

	<script src="/lib/jquery/jquery-2.2.2.min.js"></script>

    <script src="/assets/js/common.js"></script>
    <script src="/lib/jquery/plugins/jquery.slimscroll.min.js"></script>
    <script src="/assets/js/ui_common.js"></script>

    <!-- daterangepicker -->
    <link rel="stylesheet" type="text/css" href="/lib/daterangepicker/daterangepicker.css" />
    <script src="/lib/daterangepicker/moment.min.js"></script>
    <script src="/lib/daterangepicker/daterangepicker.min.js"></script>

    <!-- plupload -->
    <link href="/lib/plupload-2.1.9/jquery.plupload.queue/css/jquery.plupload.queue.custom.css" rel="stylesheet" media="screen">
	<script src="/lib/plupload-2.1.9/plupload.full.min.js"></script>
	<script src="/lib/plupload-2.1.9/jquery.plupload.queue/jquery.plupload.queue.custom.js"></script>
	<script src="/lib/plupload-2.1.9/i18n/ko.js"></script>
	<script src="/assets/js/tigris-plupload.js"></script>

</head>

<input type="hidden" name="fileUrl" 	value="${fileUrl}" />
<input type="hidden" name="fileSize" 	value="${fileSize}" />

<script type="text/javascript">
var GLB_FILE_URL 		= $("input[name='fileUrl']").val();
var GLB_SITE_FILE_SIZE 	= $("input[name='fileSize']").val(); // 첨부파일 용량
</script>