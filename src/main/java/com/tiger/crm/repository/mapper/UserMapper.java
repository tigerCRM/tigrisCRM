package com.tiger.crm.repository.mapper;

import com.tiger.crm.common.util.TigrisMap;
import com.tiger.crm.repository.domain.TUser;
import com.tiger.crm.repository.dto.user.UpOrgLeaderDto;
import com.tiger.crm.repository.dto.user.UserSearchDto;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper
{

	UserSearchDto getUserInfoByLoginEmail(String email);

	List<TUser> getLegalOrgUserList(String legalOrgCode);

	TUser getUserInfo(String userId);

	int insertUser(TUser tUser);

	int updateUser(TUser tUser);

	List<TUser> selectDeletedUserList(HashMap<String, Object> paramMap);

	int deleteUser(TUser tUser);

	UserSearchDto getUserInfoByUserEmpNo(String userEmpNo);

	UserSearchDto getUserInfoByUserId(String userId);

	UpOrgLeaderDto getUpOrgLeaderInfo(UpOrgLeaderDto upOrgLeaderDto);

	List<TigrisMap> getOrgPersonList(UserSearchDto userSearchDto);

}