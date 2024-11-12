//package com.example.crm.service.feed;
//
//import com.example.crm.core.util.DateUtils;
//import com.example.crm.repository.domain.TFeed;
//import com.example.crm.repository.domain.TFeedHistory;
//import com.example.crm.repository.domain.TFile;
//import com.example.crm.repository.domain.TPrintHistory;
//import com.example.crm.repository.dto.feed.FeedDeleteDto;
//import com.example.crm.repository.dto.feed.FeedHistoryDto;
//import com.example.crm.repository.dto.feed.FeedInfoDto;
//import com.example.crm.repository.dto.feed.FeedSearchDto;
//import com.example.crm.repository.enumerate.FeedHistoryType;
//import com.example.crm.repository.enumerate.State;
//import com.example.crm.repository.mapper.FeedMapper;
//import com.example.crm.repository.mapper.FileMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service("feedService")
//@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
//public class FeedServiceImpl implements FeedService
//{
//	@Autowired private FeedMapper feedMapper;
//	@Autowired private FileMapper fileMapper;
//
//	@Override
//	public List<TFeed> getFeeds(FeedSearchDto feedSearchDto)
//	{
//		List<TFeed> feedList = feedMapper.getFeeds(feedSearchDto);
//		for(int i=0; i<feedList.size(); i++)
//		{
//			feedList.get(i).setFeedStateName(State.ofName(feedList.get(i).getFeedState()));
//		}
//
//		return feedList;
//	}
//
//	@Override
//	public int getFeedsCount(FeedSearchDto feedSearchDto)
//	{
//		return feedMapper.getFeedsCount(feedSearchDto);
//	}
//
//	@Override
//	public TFeed getFeedDetail(FeedSearchDto feedSearchDto)
//	{
//		return feedMapper.getFeedDetail(feedSearchDto);
//	}
//
//	@Override
//	public int insert(TFeed tFeed)
//	{
//		return feedMapper.insert(tFeed);
//	}
//
//	@Override
//	public int insertFeedHistory(TFeedHistory tFeedHistory)
//	{
//		return feedMapper.insertFeedHistory(tFeedHistory);
//	}
//
//	@Override
//	public List<FeedHistoryDto> getFeedHistory(String feedId)
//	{
//		List<FeedHistoryDto> historyList = feedMapper.getFeedHistory(feedId);
//		for(int i=0; i<historyList.size(); i++)
//		{
//			historyList.get(i).setHistoryType(FeedHistoryType.ofName(historyList.get(i).getHistoryType()));
//		}
//
//		return historyList;
//	}
//
//	@Override
//	public int udpateFeedState(TFeed tFeed)
//	{
//		return feedMapper.udpateFeedState(tFeed);
//	}
//
//	@Override
//	public int update(TFeed tFeed)
//	{
//		return feedMapper.update(tFeed);
//	}
//
//	@Override
//	public int updateReview(TFeed tFeed)
//	{
//		return feedMapper.updateReview(tFeed);
//	}
//
//	@Override
//	public int createReply(TFeed tFeed)
//	{
//		return feedMapper.createReply(tFeed);
//	}
//
//	@Override
//	public List<TFeed> getReplyList(TFeed feed)
//	{
//		List<TFeed> replyList = feedMapper.getReplyList(feed);
//
//		for(int i=0; i<replyList.size(); i++)
//		{
//			String createDtStr = DateUtils.getFormatDateString("yyyy-MM-dd HH:mm:ss", replyList.get(i).getCreateDt());
//			replyList.get(i).setCreateDtStr(createDtStr);
//			String updateDtStr = DateUtils.getFormatDateString("yyyy-MM-dd HH:mm:ss", replyList.get(i).getUpdateDt());
//			replyList.get(i).setUpdateDtStr(updateDtStr);
//
//			List<TFile> replyFileList = fileMapper.getReplyFileList(replyList.get(i));
//			replyList.get(i).setReplyFileList(replyFileList);
//		}
//
//		return replyList;
//	}
//
//	@Override
//	public int deleteReply(TFeed tFeed)
//	{
//		return feedMapper.deleteReply(tFeed);
//	}
//
//	@Override
//	public TFeed getReplyInfo(TFeed tFeed)
//	{
//		TFeed feedInfo = feedMapper.getReplyInfo(tFeed);
//
//		// 날짜
//		String createDtStr = DateUtils.getFormatDateString("yyyy-MM-dd HH:mm:ss", feedInfo.getCreateDt());
//		feedInfo.setCreateDtStr(createDtStr);
//		String updateDtStr = DateUtils.getFormatDateString("yyyy-MM-dd HH:mm:ss", feedInfo.getUpdateDt());
//		feedInfo.setUpdateDtStr(updateDtStr);
//
//		// 파일
//		List<TFile> replyFileList = fileMapper.getReplyFileList(feedInfo);
//		feedInfo.setReplyFileList(replyFileList);
//
//		return feedInfo;
//	}
//
//	@Override
//	public int updateReply(TFeed tFeed)
//	{
//		return feedMapper.updateReply(tFeed);
//	}
//
//	@Override
//	public int updateReviewCompleteDate(TFeed tFeed)
//	{
//		return feedMapper.updateReviewCompleteDate(tFeed);
//	}
//
//	@Override
//	public int insertPrintHistory(TPrintHistory tPrintHistory)
//	{
//		return feedMapper.insertPrintHistory(tPrintHistory);
//	}
//
//	@Override
//	public FeedInfoDto getFeedInfoForMail(TFeed tFeed)
//	{
//		return feedMapper.getFeedInfoForMail(tFeed);
//	}
//
//	@Override
//	public int deleteFeed(FeedDeleteDto feedDeleteDto)
//	{
//		return feedMapper.deleteFeed(feedDeleteDto);
//	}
//
//}
