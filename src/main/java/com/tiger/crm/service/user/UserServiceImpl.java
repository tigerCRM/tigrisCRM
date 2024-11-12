package com.tiger.crm.service.user;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.crypto.AES256Utils;
import com.tiger.crm.common.util.TigrisMap;
import com.tiger.crm.repository.domain.TUser;
import com.tiger.crm.repository.dto.user.UpOrgLeaderDto;
import com.tiger.crm.repository.dto.user.UserSearchDto;
import com.tiger.crm.repository.mapper.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class UserServiceImpl implements UserService
{

	@Autowired private ConfigProperties config;
	@Autowired private UserMapper 		userMapper;

	@Override
	public UserSearchDto getUserInfoByLoginEmail(String email)
	{
		return userMapper.getUserInfoByLoginEmail(email);
	}

	@Override
	public UserSearchDto getCookie(HttpServletRequest request)
	{
		UserSearchDto userSearchDto = new UserSearchDto();

		try
		{
			AES256Utils aes256Utils = new AES256Utils(config.getAesSecretKey());

			Cookie[] cookies = request.getCookies();
			if(cookies != null)
			{
				for(Cookie cookie : cookies)
				{
					if(cookie.getName().equals(config.getCookieName()))
					{
						String[] userInfo = aes256Utils.decrypt(cookie.getValue()).split(":");
						if(userInfo.length == 9)
						{
							userSearchDto.setUserId(userInfo[0]);
							userSearchDto.setOrgCode(userInfo[1]);
							userSearchDto.setOrgName(userInfo[2]);
							userSearchDto.setChiefStaffNo(userInfo[3]);
							userSearchDto.setChiefStaffNm(userInfo[4]);
							userSearchDto.setLoginEmail(userInfo[5]);
							userSearchDto.setUserName(userInfo[6]);
							userSearchDto.setJobGrade(userInfo[7]);
							userSearchDto.setTeamLeaderId(userInfo[8]);
						}
					}
				}
			}
		}
		catch (UnsupportedEncodingException | GeneralSecurityException e)
		{
			e.printStackTrace();
			return userSearchDto;
		}

		return userSearchDto;
	}

	@Override
	public void setCookie(UserSearchDto userInfo, HttpServletResponse response) {

	}

//	@Override
//	public void setCookie(UserSearchDto userInfo, HttpServletResponse response)
//	{
//		try
//		{
//			AES256Utils aes256Utils = new AES256Utils(config.getAesSecretKey());
//
//			CookieGenerator cookie = new CookieGenerator();
//			cookie.setCookieName(config.getCookieName());
//			cookie.setCookiePath(CookieGenerator.DEFAULT_COOKIE_PATH);
//			cookie.setCookieDomain(config.getCookieDomain());
//			cookie.setCookieMaxAge(-1);
//			cookie.addCookie(response, aes256Utils.encrypt(userInfo.toStringForCookie()));
//		}
//		catch (UnsupportedEncodingException | GeneralSecurityException e)
//		{
//			e.printStackTrace();
//		}
//	}

	@Override
	public List<TUser> getLegalOrgUserList(String legalOrgCode)
	{
		return userMapper.getLegalOrgUserList(legalOrgCode);
	}

	@Override
	public TUser getUserInfo(String userId)
	{
		return userMapper.getUserInfo(userId);
	}

	@Override
	public void deleteCookie(HttpServletRequest request, HttpServletResponse response)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies != null)
		{
			for(Cookie cookie : cookies)
			{
				if(cookie.getName().equals(config.getCookieName()))
				{
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	@Override
	public UserSearchDto getUserInfoByUserEmpNo(String userEmpNo)
	{
		return userMapper.getUserInfoByUserEmpNo(userEmpNo);
	}

	@Override
	public UserSearchDto getUserInfoByUserId(String userId)
	{
		return userMapper.getUserInfoByUserId(userId);
	}

	@Override
	public UpOrgLeaderDto getUpOrgLeaderInfo(UpOrgLeaderDto upOrgLeaderDto)
	{
		return userMapper.getUpOrgLeaderInfo(upOrgLeaderDto);
	}

	@Override
	public List<TigrisMap> getOrgPersonList(UserSearchDto userSearchDto)
	{
		if(userSearchDto.getKeyword() != null)
		{
			userSearchDto.setKeyword("%"+userSearchDto.getKeyword()+"%");
		}

		return userMapper.getOrgPersonList(userSearchDto);
	}

}
