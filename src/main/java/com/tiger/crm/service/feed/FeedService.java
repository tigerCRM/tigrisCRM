//package com.example.crm.service.feed;
//
//import com.example.crm.repository.domain.TFeed;
//import com.example.crm.repository.domain.TFeedHistory;
//import com.example.crm.repository.domain.TPrintHistory;
//import com.example.crm.repository.dto.feed.FeedDeleteDto;
//import com.example.crm.repository.dto.feed.FeedHistoryDto;
//import com.example.crm.repository.dto.feed.FeedInfoDto;
//import com.example.crm.repository.dto.feed.FeedSearchDto;
//
//import java.util.List;
//
//public interface FeedService
//{
//	List<TFeed> getFeeds(FeedSearchDto feedSearchDto);
//
//	int getFeedsCount(FeedSearchDto feedSearchDto);
//
//	TFeed getFeedDetail(FeedSearchDto feedSearchDto);
//
//	int insert(TFeed tFeed);
//
//	int insertFeedHistory(TFeedHistory tFeedHistory);
//
//	List<FeedHistoryDto> getFeedHistory(String feedId);
//
//	int udpateFeedState(TFeed tFeed);
//
//	int update(TFeed tFeed);
//
//	int updateReview(TFeed tFeed);
//
//	int createReply(TFeed tFeed);
//
//	List<TFeed> getReplyList(TFeed feed);
//
//	int deleteReply(TFeed tFeed);
//
//	TFeed getReplyInfo(TFeed tFeed);
//
//	int updateReply(TFeed tFeed);
//
//	int updateReviewCompleteDate(TFeed tFeed);
//
//	int insertPrintHistory(TPrintHistory tPrintHistory);
//
//	FeedInfoDto getFeedInfoForMail(TFeed tFeed);
//
//	int deleteFeed(FeedDeleteDto feedDeleteDto);
//}
