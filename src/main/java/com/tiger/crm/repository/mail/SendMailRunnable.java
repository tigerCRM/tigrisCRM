//package com.example.crm.repository.mail;
//
//import com.tigrison.core.TigrisGlobal;
//import com.tigrison.core.context.ConfigProperties;
//import com.tigrison.core.crypto.AES256Utils;
//import com.tigrison.core.util.BeanUtils;
//import com.tigrison.repository.domain.TFeed;
//import com.tigrison.repository.domain.TMailHistory;
//import com.tigrison.repository.domain.TUser;
//import com.tigrison.repository.dto.feed.FeedInfoDto;
//import com.tigrison.service.feed.FeedService;
//import com.tigrison.service.mail.MailHistoryService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.UnsupportedEncodingException;
//import java.security.GeneralSecurityException;
//
//public class SendMailRunnable implements Runnable
//{
//	private Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//	private ConfigProperties configProperties;
//
//	private TFeed 	tFeed;
//	private TUser 	tUser;
//	private String 	senderId;
//
//	public SendMailRunnable(TFeed tFeed, TUser tUser, String senderId)
//	{
//		this.configProperties 	= (ConfigProperties) BeanUtils.findBean("configProperties");
//		this.tFeed 				= tFeed;
//		this.tUser 				= tUser;
//		this.senderId 			= senderId;
//	}
//
//	@Override
//	public void run()
//	{
//		MailHistoryService 	mailHistoryService 	= (MailHistoryService) BeanUtils.findBean("mailHistoryService");
//		FeedService 		feedService 		= (FeedService) BeanUtils.findBean("feedService");
//
//		// 간단한 피드 정보
//		FeedInfoDto feedInfo = feedService.getFeedInfoForMail(this.tFeed);
//
//		String mailHistoryId 	= TigrisGlobal.generateID();
//		String subject 			= "";
//		String content 			= "";
//		String toEmailAddress 	= "";
//		String toName 			= "";
//		String mailLink 		= "";
//
//		toEmailAddress 	= this.tUser.getLoginEmail();
//		toName 			= this.tUser.getUserName();
//		mailLink 		= this.configProperties.getMailLinkPrev() + getLinkParam(this.tFeed.getCommunityId(), toEmailAddress);
//
//		// 게시글 상태에 따른 메일 제목 세팅 및 링크(임시) 세팅
//		String feedState = this.tFeed.getFeedState();
//		switch(feedState)
//		{
//		// 승인대기
//		case "WAITING_FOR_APPROVAL":
//			subject = this.configProperties.getMailSubjectWaitingForApproval();
//			break;
//		// 검토의뢰
//		case "REQUEST_FOR_REVIEW":
//			subject = this.configProperties.getMailSubjectRequestForReview();
//			break;
//		// 검토중
//		case "UNDER_REVIEW":
//			subject = this.configProperties.getMailSubjectUnderReview();
//			break;
//		// 수정요구
//		case "REQUEST_FOR_CORRECTION":
//			subject = this.configProperties.getMailSubjectRequestForCorrection();
//			break;
//		// 반려
//		case "REJECT":
//			subject = this.configProperties.getMailSubjectReject();
//			break;
//		// 검토완료
//		case "REVIEW_COMPLETED":
//			subject = this.configProperties.getMailSubjectReviewCompleted();
//			break;
//		// 댓글
//		case "REPLY":
//			subject = this.configProperties.getMailSubjectReply();
//			break;
//		default:
//			subject = "[법무시스템] 알림 발송";
//			break;
//		}
//
//		// 테스트용 계정 링크 세팅
//		if(0 <= this.senderId.indexOf("1f63bf0db021ee0cc9c6a72aa99"))
//		{
//			toEmailAddress = "seastar@tigrison.com";
//			switch(feedState)
//			{
//				// 승인대기
//				case "WAITING_FOR_APPROVAL":
//				case "REQUEST_FOR_REVIEW":
//					mailLink = this.configProperties.getMailLinkPrev() + getLinkParam(this.tFeed.getCommunityId(), "legal@mf.seegene.com");
//					break;
//				case "REPLY":
//					if(tUser.getOrgCode().equals(configProperties.getLegalOrgCode()))
//					{
//						mailLink = this.configProperties.getMailLinkPrev() + getLinkParam(this.tFeed.getCommunityId(), "legal@mf.seegene.com");
//					}
//					else
//					{
//						mailLink = this.configProperties.getMailLinkPrev() + getLinkParam(this.tFeed.getCommunityId(), "user@mf.seegene.com");
//					}
//					break;
//				default:
//					mailLink = this.configProperties.getMailLinkPrev() + getLinkParam(this.tFeed.getCommunityId(), "user@mf.seegene.com");
//					break;
//			}
//		}
//
//		// 메일 내용 세팅
//		content = this.configProperties.getMailContent().replace("{mailLink}", mailLink);
//
//		content += "<br/><br/><br/>";
//		content += "게시물 종류 : " + feedInfo.getCommunityName() + "<br/><br/>";
//		content += "게시물 제목 : " + feedInfo.getTitle() + "<br/><br/>";
//		content += "작성자 정보 : " + feedInfo.getOrgName() + " " + feedInfo.getUserName() + " " + feedInfo.getJobgrade();
//
//		LOGGER.debug("tFeed : " + this.tFeed);
//		LOGGER.debug("tUser : " + this.tUser);
//		LOGGER.debug("senderId : " + this.senderId);
//		LOGGER.debug("mailLink : " + mailLink);
//		LOGGER.info("communmityId : " + this.tFeed.getCommunityId());
//		LOGGER.info("subject : " + subject);
//		LOGGER.info("content : " + content);
//		LOGGER.info("toEmailAddress : " + toEmailAddress);
//		LOGGER.info("toName : " + toName);
//
//		// 메일 발송
//		new MailSender().sendMail(subject, content, toEmailAddress, toName, mailHistoryId);
//
//		// 메일 발송이력 등록
//		TMailHistory tMailHistory = new TMailHistory(senderId);
//		tMailHistory.setMailHistoryId(mailHistoryId);
//		tMailHistory.setFeedId(this.tFeed.getFeedId());
//		tMailHistory.setUserId(this.tUser.getUserId());
//		tMailHistory.setSenderEmail(this.configProperties.getMailSmtpAuthDefaultAccountEmail());
//		tMailHistory.setReceiverName(toName);
//		tMailHistory.setReceiverEmail(toEmailAddress);
//		tMailHistory.setTitle(subject);
//		tMailHistory.setContents(content);
//		tMailHistory.setLinkUrl(mailLink);
//
//		mailHistoryService.insertMailHistory(tMailHistory);
//
//		try
//		{
//			// 작업을 끝내고 0.3초 sleep
//			Thread.sleep(300);
//		}
//		catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//
//
//	public String getLinkParam(String communmityId, String toEmailAddress)
//	{
//		String result = "";
//		try
//		{
//			AES256Utils aes256Utils = new AES256Utils(configProperties.getAesSecretKey());
//			String 		param 		= communmityId + ":" + toEmailAddress;
//
//			result = aes256Utils.encrypt(param);
//		}
//		catch (UnsupportedEncodingException | GeneralSecurityException e)
//		{
//			e.printStackTrace();
//		}
//
//		return result;
//	}
//
//}
