package com.tiger.crm.common.context;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("ConfigProperties")
@Getter
public class ConfigProperties {

	@Value("${spring.profiles.active}")
	private String profile;

	@Value("${cookie.domain}")
	private String cookieDomain;

	@Value("${cookie.name}")
	private String cookieName;

	// File
//	@Value("${file.upload.path}")
//	private String fileUploadPath;
//
//	@Value("${file.url}")
//	private String fileUrl;
//
//	@Value("${file.size}")
//	private String fileSize;

	// legalOrgCode
//	@Value("${legal.org.code}")
//	private String legalOrgCode;

	// enc
	@Value("${aes256.secret.key}")
	private String aesSecretKey;

	// mail
//	@Value("${mail.link.prev}")
//	private String mailLinkPrev;
//
//	@Value("${mail.subject.waitingForApproval}")
//	private String mailSubjectWaitingForApproval;
//
//	@Value("${mail.subject.requestForReview}")
//	private String mailSubjectRequestForReview;
//
//	@Value("${mail.subject.requestForCorrection}")
//	private String mailSubjectRequestForCorrection;
//
//	@Value("${mail.subject.reject}")
//	private String mailSubjectReject;
//
//	@Value("${mail.subject.reviewCompleted}")
//	private String mailSubjectReviewCompleted;
//
//	@Value("${mail.subject.underReview}")
//	private String mailSubjectUnderReview;
//
//	@Value("${mail.subject.reply}")
//	private String mailSubjectReply;
//
//	@Value("${mail.content}")
//	private String mailContent;
//
//	@Value("${mail.debug}")
//	private String mailDebug;
//
//	@Value("${mail.transport.protocol}")
//	private String mailTransportProtocol;
//
//	@Value("${mail.smtp.encoding}")
//	private String mailSmtpEncoding;
//
//	@Value("${mail.smtp.host}")
//	private String mailSmtpHost;
//
//	@Value("${mail.smtp.port}")
//	private String mailSmtpPort;
//
//	@Value("${mail.smtp.starttls.enable}")
//	private String mailSmtpStarttlsEnable;
//
//	@Value("${mail.smtp.socketFactory.port}")
//	private String mailSmtpSocketFactoryPort;
//
//	@Value("${mail.smtp.socketFactory.class}")
//	private String mailSmtpSocketFactoryClass;
//
//	@Value("${mail.smtp.socketFactory.fallback}")
//	private String mailSmtpSocketFactoryFallback;
//
//	@Value("${mail.smtp.auth}")
//	private String mailSmtpAuth;
//
//	@Value("${mail.smtp.auth.default.account.name}")
//	private String mailSmtpAuthDefaultAccountName;
//
//	@Value("${mail.smtp.auth.default.account.email}")
//	private String mailSmtpAuthDefaultAccountEmail;
//
//	@Value("${mail.smtp.auth.default.account.id}")
//	private String mailSmtpAuthDefaultAccountId;
//
//	@Value("${mail.smtp.auth.default.account.pwd}")
//	private String mailSmtpAuthDefaultAccountPwd;
//
//	@Value("${mail.activation}")
//	private String mailActivation;
}
