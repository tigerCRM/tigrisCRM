package com.tiger.crm.common;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <code>ErrorCode</code>
 *
 *
<blockquote><pre>
ERROR_CODE_LIST	
성공 코드: 0		
		
GROUP   KEY DESCRIPTION
세션 종료	-1	세션 끊김
		
로그인	100	로그인 실패
	101	디바이스 토큰 등록 실패
	102	등록된 디바이스가 아닌 다른 디바이스로 로그인
		
Common	200	Common 에러
	201	UserMenu 없음
	202	피드의 상태가 없을 경우의 에러
	203	소셜 아이디가 없을 경우의 에러
	204	조직 혹은 멤버의 리스트가 더 이상 존재하지 않을 경우
	205	알림 설정에 실패했을 경우의 에러
	206	알림 내역이 더 이상 존재하지 않을 경우
	207	알림 읽음 처리가 실패했을 경우
	208	알림 카운트를 가져오는 것을 실패했을 경우의 에러
	209	알림 카운트의 리셋하는 것을 실패했을 경우의 에러
	210	프로필 업데이트를 실패했을 경우의 에러
		
타임라인	300	피드 관련 에러
	301	피드없음
	302	잘못된 피드 아이디'를 파라미터로 보냄
	303	감정 표현이 실패했을 때의 에러
	304	감정 표현을 철회할 때, 실패했을 경우의 에러
	305	피드 삭제 실패
	306	조회된 멤버 없음
	307	피드를 즐겨찾기 등록하거나 해제를 실패할 때 에러
	308	참석 여부를 진행하는 과정에서 실패했을 경우의 에러
	309	참석자/불참자/미답변자 등의 리스트를 가져오는데 실패했을 경우의 에러
	310	설문에 답하는 과정에서 실패했을 경우의 에러
	311	답한 설문 항목을 해제하는 과정에서 실패했을 경우의 에러
	312	각 설문 항목에 응답한 멤버 가져오는데 실패했을 경우의 에러
	313	개인별 작성 게시물 리스트를 가져오는데 실패했을 경우의 에러
		
피드 작성	400	피드 공유자/피드 공유 커뮤니티 없음
	401	링크가 없음
	402	링크 정보가 없음
	403	필수 피드 아이디가 없음
	404	부적절한 사용자가 변경을 요쳥한 경우
	405	적절한 멘션 대상자가 없을 경우
	406	멘션 대상자를 가져오는데 실패했을 경우의 에러
	407	참석자/불참자/미답변자 등의 리스트가 없는 경우
	408	설문 항목 별 대상자의 리스트가 없는 경우
	409	개인별 작성 게시물 리스트가 없는 경우
		
사용자	500	비밀번호 변경시 기존 비밀번호가 일치하지 않을 경우
	501	프로필 정보를 업데이틑 하는 과정에서 실패했을 경우의 에러
	502	아이디/비밀번호를 찾을 때 존재하지 않는 유저 아이디인 경우
	503	아이디 찾는 과정에서 실패했을 경우의 에러
	504	비밀번호를 찾는 과정에서 실패했을 경우의 에러
	505	디바이스 인증 요청이 이전에 시도되었을 경우
	506	멤버 즐겨찾기하는 과정에서 실패했을 경우의 에러
	507	다수의 멤버 즐겨찾기하는 과정에서 실패했을 경우의 에러
	508	디바이스 인증 요청하는 과정에서 실패했을 경우의 에러
	509	본인이 속한 사이트 리스트 가져오는데 실패했을 경우의 에러
	510	사이트 전환하는 과정에서 실패했을 경우의 에러
		
고객센터	602	본인의 문의내역을 가져오는 과정에서 실패했을 경우의 에러
	603	본인의 문의내역이 더 이상 존재하지 않는 경우
	604	특정 문의사항의 댓글을 가져오는 과정에서 실패했을 경우의 에러
	605	특정 문의사항의 댓글이 더 이상 존재하지 않는 경우
	606	공지사항들을 가져오는 과정에서 실패했을 경우의 에러
	607	공지사항이 없는 경우
	608	특정 문의사항의 상세내역을 가져오는 과정에서 실패했을 경우의 에러
	609	특정 문의사항의 완료 여부를 표시하는 과정에서 실패했을 경우의 에러
	610	문의 사항을 입력하는 과정에서 실패했을 경우의 에러
		
커뮤니티	700	커뮤니티 정보를 가져오는데 실패했을 경우의 에러
	701	특정 커뮤니티의 멤버를 가져오는데 실패했을 경우의 에러
	702	커뮤니티 생성하는 과정에서 실패했을 경우의 에러
	703	초대되는 멤버가 API로 전달되지 않은 경우
	704	커뮤니티 정보를 업데이트 하는 과정에서 실패했을 경우의 에러
	705	탈퇴하는 멤버가 API로 전달되지 않은 경우
	706	커뮤니티 가입 신청 시 이미 커뮤니티의 멤버인 경우
	707	비공개 커뮤니티에 가입 신청을 한 경우
	708	가입 대기 상태에 있는 리스트가 없는 경우
	709	커뮤니티에 멤버를 추가하는 과정에서 실패했을 경우의 에러
	711	멤버를 초대할 때 중복된 멤버가 있는 경우
	712	커뮤니티의 멤버를 강제 탈퇴하는 과정에서 실패했을 경우의 에러
	713	가입 신청 대기의 멤버를 승인하는 과정에서 실패했을 경우의 에러
	714	커뮤니티를 즐겨찾기 하는 과정에서 실패했을 경우의 에러
	715	검색하고자 하는 커뮤니티가 존재하지 않는 경우
	716	커뮤니티 가입 신청하는 과정에서 실패했을 경우의 에러
	717	커뮤니티를 탈퇴하는 과정에서 실패했을 경우의 에러
	718	커뮤니티 생성시 프리셋을 가져오는 데 실패했을 경우의 에러
		
메신저	800	메신저 방의 내용이 없는 경우
		
		
캘린더	900	캘린더 정보 요청 시 API에 값이 잘못 전달 된 경우
	901	구글 캘린더와 동기화할 때 구글의 계정과 연동이 되어 있지 않은 경우
	903	구글 캘린더와 동기화 하는 과정에서 실패했을 경우의 에러

</pre></blockquote>
 * 
 */
public enum ErrorCode {

	FAIL(-1, "error.common.fail"),
	SUCCESS(0, "error.common.success"),

	SESSION_FAIL(-1, "error.session.fail"),
	SOCKET_FAIL(-1, "error.socket.fail"),

	LOGIN_INVALID_USER(100, "error.login.invalidUser"),
	LOGIN_REGISTER_TOKEN(101, "error.login.registerToken"),
	LOGIN_DIFFERENT_DEVICE_ID(102, "error.login.differentDeviceId"),

	COMMON_ERROR(200, "error.common.error"),
	COMMON_NO_USER_MENU(201, "error.common.noUserMenu"),
	COMMON_NO_WRITE_STATUS_TYPE(202, "error.common.noWriteStatusType"),
	COMMON_NO_SOCIAL_ID(203, "error.common.noSocialId"),
	COMMON_NO_MORE_ORG_OR_MEMBER(204, "error.common.noMoreOrgOrMember"),
	COMMON_FAIL_SAVE_NOTI(205, "error.common.fail.save.noti"),
	COMMON_NO_NOTI_LIST(206, "error.common.no.noti.list"),
	COMMON_FAIL_TO_READ_NOTI(207, "error.common.failToReadNoti"),
	COMMON_FAIL_TO_GET_NOTI_COUNT(208, "error.common.failToGetNotiCount"),
	COMMON_FAIL_TO_SET_NOTI_COUNT(209, "error.common.failToSetNotiCount"),
	COMMON_FAIL_TO_UPLOAD_FILE(210, "error.common.failToUploadFile"),
	GOOGLE_FAIL_TO_AUTHENTICATION(211, "error.google.failToAuthentication"),
	COMMON_NO_MORE_ORG_LIST(212, "error.common.noMoreOrgList"),
	COMMON_NO_MORE_MEMBER_LIST(213, "error.common.noMoreMemberList"),

	FEED_ERROR(300, "error.feed.error"),
	FEED_NO_FEED_LIST(301, "error.feed.nofeedlist"),
	FEED_INCORRECT_FEED_ID(302, "error.feed.incorrectfeedid"),
	FEED_FAIL_TO_SET_EMOTION(303, "error.feed.failtosetemotion"),
	FEED_FAIL_TO_UNSET_EMOTION(304, "error.feed.failtounsetemotion"),
	FEED_FAIL_TO_DELETE_FEED(305, "error.feed.faildeletefeed"),
	FEED_NO_MEMBER_LIST(306, "error.feed.nomemberlist"),
	FEED_FAIL_TO_SET_FAVORITE(307, "error.feed.failtosetfavorite"),
	FEED_FAIL_TO_ANSWER_PARTICIPANT(308, "error.feed.failToAnswerParticipant"),
	FEED_FAIL_TO_GET_PARTICIPANTS(309, "error.feed.failToGetParticipants"),
	FEED_FAIL_TO_SELECT_SURVEY(310, "error.feed.failToSelectSurvey"),
	FEED_FAIL_TO_DESELECT_SURVEY(311, "error.feed.failToDeselectSurvey"),
	FEED_FAIL_TO_GET_SURVEY_MEMBER(312, "error.feed.failToGetSurveyItem"),
	FEED_FAIL_TO_GET_WRITTEN_LIST(313, "error.flower.failToGetWrittenList"),

	FEED_NO_SHARE_LIST(400, "error.feed.nosharelist"),
	FEED_NO_URL_LINK(401, "error.feed.noUrlLink"),
	FEED_NO_URL_INFO(402, "error.feed.noUrlInfo"),
	FEED_NO_REQUIRD_FEED_ID(403, "error.feed.noRequiredFeedId"),
	FEED_INCORRECT_USER_ACCESS(404, "error.feed.incorrectUserAccess"),
	FEED_NO_MENTION_MEMBER(405, "error.mention.noMember"),
	FEED_FAIL_TO_SEARCH_MENTION(406, "error.mention.failToSearchMention"),
	FEED_NO_PARTICIPANTS(407, "error.feed.noParticipants"),
	FEED_NO_SURVEY_MEMBER(408, "error.feed.noSurveyMember"),
	FEED_NO_WRITTEN_LIST(409, "error.flower.noWrittenList"),

	USER_INVALID_PASSWD(500,"error.user.invalidPasswd"),
	USER_FAIL_TO_UPDATE_INFO(501,"error.user.failToUpdateInfo"),
	USER_INVALID(502,"error.user.invalid"),
	USER_FAIL_TO_FIND_ID(503,"error.user.failToFindId"),
	USER_FAIL_TO_FIND_PW(504,"error.user.failToFindPw"),
	USER_YES_DEVICE_AUTH_REQUEST_CHANGE(505,"error.user.yesDeviceAuthRequestChange"),
	SOCAIL_FAIL_TO_SET_FAVORITE_PRESON(506,"error.social.failToSetFavoritePerson"),
	SOCAIL_FAIL_TO_GET_FAVORITE_PEOPLE(507,"error.social.failToGetFavoritePeople"),
	USER_FAIL_TO_REQUEST_DEVICE_AUTH(508,"error.user.failToRequestDeviceAuth"),
	SOCAIL_FAIL_TO_GET_SITE_LIST(509,"error.social.failToGetSiteList"),
	SOCAIL_FAIL_TO_CHANGE_SITE(510,"error.social.failToChangeSite"),
	USER_NO_USER(511,"error.user.noUser"),
	USER_FAIL_TO_SEARCH_USER(512,"error.user.failToSearchUser"),
	ACTIVE_USER_COUNT_EXCEED(513,"error.user.activeUserCountExceed"),
	PASSWORD_PATTERN_FAIL(514,"error.password.failPattern"),
	CURRENT_PASSWORD_EQUAL_NEW_PASSWORD_FAIL(515,"error.password.equal.newPassword"),


	DB_KEY_DUPLICATE(600,"error.db.keyDuplicate"),
	DB_NO_TASK_DATA(601,"error.db.noTaskData"),
	CUSTOMER_FAIL_TO_GET_MY_QUESTIONS(602,"error.customer.failToGetMyQuestions"),
	CUSTOMER_NO_MY_QUESTIONS(603,"error.customer.noMyQuestions"),
	CUSTOMER_FAIL_TO_GET_REPLIES(604,"error.customer.failToGetReplies"),
	CUSTOMER_NO_REPLIES(605,"error.customer.noReplies"),
	CUSTOMER_FAIL_TO_GET_NOTICES(606,"error.customer.failToGetNotices"),
	CUSTOMER_NO_NOTICES(607,"error.customer.noNotices"),
	CUSTOMER_FAIL_TO_GET_QUESTION_DETAIL(608,"error.customer.failToGetQuestionDetail"),
	CUSTOMER_FAIL_TO_COMPLETE_QUESTION(609,"error.customer.failToSetComplete"),
	CUSTOMER_FAIL_TO_CREATE_QUESTION(610,"error.customer.failToCreateQuestion"),

	COMMUNITY_NO_INFO(700,"error.community.noInfo"),
	COMMUNITY_NO_MEMBERS(701,"error.community.noMembers"),
	COMMUNITY_CREATE_FAIL(702,"error.community.createFail"),
	COMMUNITY_NO_ADD_MEMBER(703,"error.community.noAddMember"),
	COMMUNITY_FAIL_TO_UPDATE(704,"error.community.failToUpdate"),
	COMMUNITY_NO_DELETE_MEMBER(705,"error.community.noDeleteMember"),
	COMMUNITY_JOIN_MEMBER(706,"error.community.joinMember"),
	COMMUNITY_NOT_JOIN_PRIVATE(707,"error.community.notJoinPrivate"),
	COMMUNITY_NO_APPROBATION_MEMBER(708,"error.community.noApprobationMember"),
	COMMUNITY_FAIL_TO_ADD_MEMBERS(709,"error.community.failToAddMembers"),
	COMMUNITY_NO_MEMBER_TYPE(710,"error.community.noMemberType"),
	COMMUNITY_DUPLICATED_MEMBERS(711,"error.community.duplicatedMembers"),
	COMMUNITY_FAIL_TO_DELETE_MEMBERS(712,"error.community.failToDeleteMembers"),
	COMMUNITY_FAIL_TO_ACCEPT_MEMBERS(713,"error.community.failToAcceptMembers"),
	COMMUNITY_FAIL_TO_SET_FAVORITE(714, "error.community.failToSetFavorite"),
	COMMUNITY_NO_SEARCH_LIST(715, "error.community.noSearchList"),
	COMMUNITY_FAIL_TO_JOIN(716, "error.community.failToJoin"),
	COMMUNITY_FAIL_TO_WITHDRAW(717, "error.community.failToWithDraw"),
	COMMUNITY_FAIL_TO_GET_PRESET(718, "error.community.failToGetPreset"),
	COMMUNITY_NOT_ACCESS(719, "error.community.notAccess"),

	CHAT_INTERNAL_SERVER_ERROR(800, "error.chat.internal.server.error"),
	CHAT_FORBIDDEN_ERROR(803, "error.chat.forbidden.error"),

	CALENDAR_SEARCH_VALUE_INVALIE(900, "error.calendar.search.value.invalid"),
	CALENDAR_GOOGLE_NOT_CONNECT(901, "error.calendar.googleNotConnect"),
	CALENDAR_FAIL_TO_SYNC_GOOGLE_CALENDAR(903, "error.calendar.syncGoogleCalendar"),

	/**
	 * 2016.09.29 추가
	 * */

	ADMIN_NO_DEVICE_CLEAR_LIST(1000,"error.no.device.clear.list"),
	ADMIN_FAIL_DEVICE_CLEAR(1001,"error.fail.device.clear"),

	UNDEFINED_ERROR_CODE(999, "error.common.undefinedErrorCode"),
	
	//WORK 관련 추가
	WORK_BASIC_SAVE_FAIL(1100, "error.work.failToBasicSave"),//업무 기본 저장 실패
	WORK_MEMBERS_SAVE_FAIL(1101, "error.work.failToMembersSave"),//업무 멤버리스트 저장 실패
	WORK_PRERELATION_SAVE_FAIL(1102, "error.work.failToPreRelationSave"),//	선후 관계 저장 실패
	WORK_DEFAULT_RELATION_SAVE_FAIL(1103, "error.work.failToDefaultRelationSave"),//기본 관계 저장 실패
	WORK_BASIC_RELATION_SAVE_FAIL(1104, "error.work.failToBasicRelationSave"),//상하 관계 저장 실패	
	WORK_BASIC_DEL_FAIL(1105, "error.work.failToBasicDel"),//업무 기본 삭제 실패
	WORK_MEMBERS_DEL_FAIL(1106, "error.work.failToMembersDel"),//업무 멤버리스트 삭제 실패
	WORK_PRERELATION_DEL_FAIL(1107, "error.work.failToPreRelationDel"),//선후 관계 삭제 실패
	WORK_DEFAULT_RELATION_DEL_FAIL(1108, "error.work.failToDefaultRelationDel"),//기본 관계 삭제 실패
	WORK_BASIC_RELATION_DEL_FAIL(1109, "error.work.failToBasicRelationDel"),//상하 관계 삭제 실패	
	WORK_BOARD_SAVE_FAIL(1110, "error.work.failToBoardSave"),//워킹 보드 저장 실패
	WORK_BOARD_DEL_FAIL(1111, "error.work.failToBoardDel"),//워킹 보드 삭제 실패
	WORK_WEIGHT_SAVE_FAIL(1112, "error.work.failToWeightSave"),//가중치 저장 실패	
	WORK_NO_PERMISSION(1113, "error.work.noPermission"),//권한 없음
	WORK_IS_CHILD_WORKGROUP(1114, "error.work.isChildWorkGroup"),//하위 업무그룹 존재
	WORK_RESULTDATE_UPDATE_FAIL(1115, "error.work.failToresultDateUpdate"),//실적일 업데이트 실패
	WORK_TEMPLATE_OPEN_BOUNDS_SAVE_FAIL(1116, "error.work.failToTemplateOpenBoundsSave"),//프로젝트 템플릿 공개 범위 저장 실패
	

	//나의 할 일 추가
	
	//스케줄 추가
	SCHEDULE_LIST_SEARCH_FAIL(1300, "error.schedule.failToListSearch");	//스케줄 목록 조회 실패
	
	
	private final int errorCode;
	private String errorMessage;
    private static final Map<Integer, String> errorCodeMap = new HashMap<Integer, String>();

    static {
        for (ErrorCode errorCode : ErrorCode.values()) {
        	errorCodeMap.put(errorCode.getErrorCode(), errorCode.getErrorMessage());
        }
    }

    ErrorCode(final int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
    	return errorMessage;
    }

    public static String getErrorMessage(final Integer i) {

    	if(i == null)
            return null;

        String errorMessage = errorCodeMap.get(i);

        return StringUtils.isEmpty(errorMessage) ? UNDEFINED_ERROR_CODE.getErrorMessage() : errorMessage;
    }
}
