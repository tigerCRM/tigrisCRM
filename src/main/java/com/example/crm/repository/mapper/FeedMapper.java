package com.example.crm.repository.mapper;

import com.example.crm.core.mapper.GenericMapper;
import com.example.crm.repository.domain.TFeed;
import com.example.crm.repository.domain.TFeedHistory;
import com.example.crm.repository.domain.TPrintHistory;
import com.example.crm.repository.dto.feed.FeedDeleteDto;
import com.example.crm.repository.dto.feed.FeedHistoryDto;
import com.example.crm.repository.dto.feed.FeedInfoDto;
import com.example.crm.repository.dto.feed.FeedSearchDto;

import java.util.List;

public interface FeedMapper extends GenericMapper<TFeed>
{
	List<TFeed> getFeeds(FeedSearchDto feedSearchDto);

	int getFeedsCount(FeedSearchDto feedSearchDto);

	TFeed getFeedDetail(FeedSearchDto feedSearchDto);

	int insertFeedHistory(TFeedHistory tFeedHistory);

	List<FeedHistoryDto> getFeedHistory(String feedId);

	int udpateFeedState(TFeed tFeed);

	int updateReview(TFeed tFeed);

	int getFeedHistory(TFeed tFeed);

	int createReply(TFeed tFeed);

	List<TFeed> getReplyList(TFeed feed);

	int deleteReply(TFeed tFeed);

	TFeed getReplyInfo(TFeed tFeed);

	int updateReply(TFeed tFeed);

	int updateReviewCompleteDate(TFeed tFeed);

	int insertPrintHistory(TPrintHistory tPrintHistory);

	FeedInfoDto getFeedInfoForMail(TFeed tFeed);

	int deleteFeed(FeedDeleteDto feedDeleteDto);
}
