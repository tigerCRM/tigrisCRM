package com.example.crm.service.user;

import com.example.crm.core.util.TigrisMap;
import com.example.crm.repository.domain.TUser;
import com.example.crm.repository.dto.user.UpOrgLeaderDto;
import com.example.crm.repository.dto.user.UserSearchDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService
{

	UserSearchDto getUserInfoByLoginEmail(String email);

	UserSearchDto getCookie(HttpServletRequest request);

	void setCookie(UserSearchDto userInfo, HttpServletResponse response);

	List<TUser> getLegalOrgUserList(String legalOrgCode);

	TUser getUserInfo(String userId);

	void deleteCookie(HttpServletRequest request, HttpServletResponse response);

	UserSearchDto getUserInfoByUserEmpNo(String userEmpNo);

	UserSearchDto getUserInfoByUserId(String userId);

	UpOrgLeaderDto getUpOrgLeaderInfo(UpOrgLeaderDto upOrgLeaderDto);

	List<TigrisMap> getOrgPersonList(UserSearchDto userSearchDto);

}
