var GLB_SEARCH_TYPE 	= 'TITLEANDCONTENTS'; // 제목+내용(TITLEANDCONTENTS), 작성자(USERNAME), 부서명(ORGNAME)
var GLB_SEARCH_KEYWORD 	= null;
var GLB_FILTER_START_DT = null;
var GLB_FILTER_END_DT 	= null;
var GLB_FILTER_PAGE 	= 1;
var GLB_COMMUNITY_ID 	= null;

function getFeeds(layerId)
{
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

	$.ajax({
		type: 'POST',
		url: '/feed/list',
		data: postData,
		dataType: 'html',
		async: false,
		success: function(data, textStatus, XMLHttpRequest) {
			$('#'+layerId).html(data);
		}
	});
}

function getStorageList(layerId)
{
	var postData = {
		 'searchType' : GLB_SEARCH_TYPE
		,'page' : GLB_FILTER_PAGE
	};

	if(GLB_SEARCH_KEYWORD != null){
		postData.searchKeyword = GLB_SEARCH_KEYWORD;
	}
	if(GLB_FILTER_START_DT != null){
		postData.filterStartDt = GLB_FILTER_START_DT;
	}
	if(GLB_FILTER_END_DT != null){
		postData.filterEndDt = GLB_FILTER_END_DT;
	}

	$.ajax({
		type: 'POST',
		url: '/storage/list',
		data: postData,
		dataType: 'html',
		async: false,
		success: function(data, textStatus, XMLHttpRequest) {
			$('#'+layerId).html(data);
		}
	});
}

function getReplyList(layerId, feedId)
{
	var postData = {
		 'feedId' : feedId
		,'layerId' : layerId
	};

	$.ajax({
		type: 'POST',
		url: '/feed/reply/list',
		data: postData,
		dataType: 'html',
		async: false,
		success: function(data, textStatus, XMLHttpRequest) {
			$('#'+layerId).html(data);
		}
	});
}

function deleteReplySubmit(layerId, feedId, upFeedId)
{
	if(!confirm('댓글을 삭제하시겠습니까?')) { return; }

	var postData = {
		'feedId' : feedId
	};

	$.ajax({
		type: 'POST',
		url: '/feed/reply/delete',
		data: postData,
		dataType: 'json',
		async: false,
		success: function(data, textStatus, XMLHttpRequest) {
			if(data.code == 0) { getReplyList(layerId, upFeedId); }
			else { alert('댓글 삭제가 실패하였습니다.'); }
		}
	});
}

function modifyReplySubmit(layerId, feedId, upFeedId)
{

}

function checkWordByte(value, maxByte) {
	var returnValue = false;

	var strValue = value;
	var strLen = strValue.length;
	var totalByte = 0;
	var len = 0;
	var oneChar = "";
	var str2 = "";

	for (var i = 0; i < strLen; i++) {
		oneChar = strValue.charAt(i);
		if (escape(oneChar).length > 4) {
			totalByte += 2;

		} else {
			totalByte++;
		}
		if (totalByte <= maxByte) {
			len = i + 1;
		}
	}
	if (totalByte > maxByte) {
		returnValue = true;
	}
	return returnValue;
}