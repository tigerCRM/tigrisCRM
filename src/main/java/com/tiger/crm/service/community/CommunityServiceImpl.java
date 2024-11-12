//package com.example.crm.service.community;
//
//import com.example.crm.repository.domain.TCommunity;
//import com.example.crm.repository.dto.community.CommunityDto;
//import com.example.crm.repository.mapper.CommunityMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CommunityServiceImpl implements CommunityService
//{
//	@Autowired CommunityMapper communityMapper;
//
//	public List<TCommunity> getCommunityList(CommunityDto communityDto)
//	{
//		return communityMapper.getCommunityList(communityDto);
//	}
//
//	@Override
//	public TCommunity getCommunityInfo(CommunityDto communityDto)
//	{
//		return communityMapper.getCommunityInfo(communityDto);
//	}
//
//	@Override
//	public int create(TCommunity tCommunity)
//	{
//		return communityMapper.create(tCommunity);
//	}
//}
