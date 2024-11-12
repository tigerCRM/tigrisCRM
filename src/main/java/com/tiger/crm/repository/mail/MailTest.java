//package com.example.crm.repository.mail;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.io.UnsupportedEncodingException;
//import java.util.Properties;
//
//public class MailTest
//{
//	public static void main(String[] args)
//	{
//		String mailHistoryId 	= "TEST..";
//		String subject 			= "제목 테스트 중입니다.";
//		String content 			= "내용 테스트 중입니다.";
//		String toEmailAddress 	= "seastar@tigrison.com";
//		String toName 			= "전해성";
//
//		new MailTest().sendMail(subject, content, toEmailAddress, toName, mailHistoryId);
//	}
//
//	public void sendMail(String subject, String content, String toEmailAddress, String toName, String mailHistoryId)
//	{
//		System.out.println("Send Mail Start.. " + mailHistoryId);
//
//		Properties props = new Properties();
//        props.put("mail.debug", 				"true");
//        props.put("mail.transport.protocol", 	"smtp");
////        props.put("mail.smtp.host", 			"smtp.mailplug.co.kr");
//        props.put("mail.smtp.host", 			"smtp.gmail.com");
//        props.put("mail.smtp.port", 			"465");
//        props.put("mail.smtp.auth", 			"true");
//        props.put("mail.smtp.starttls.enable", 	"true");
//
//        boolean smtpSarttls = true;
//        boolean smtpSocketFactorUse = true;
//
//        if (smtpSarttls) {
//            if (smtpSocketFactorUse) {
//                props.put("mail.smtp.socketFactory.port", 		"465");
//                props.put("mail.smtp.socketFactory.class", 		"javax.net.ssl.SSLSocketFactory");
//                props.put("mail.smtp.socketFactory.fallback", 	"false");
//            }
//        }
//
//        Session javaMailSession = getJavaMailSession(
//        		props,
////        		"nicloud@nicloud.kr",
////        		"tiger0314!@#$",
////        		"mywhoenterprise@tigercompany.kr",
////        		"0314?0324!",
//        		"tigrison.com@gmail.com",
//        		"trnrnzckfwisloke",
//        		"true"
//        );
//        boolean smtpDebug = false;
//        javaMailSession.setDebug(smtpDebug);
//
//        MimeMessage msg = new MimeMessage(javaMailSession);
//
//        InternetAddress addressFrom;
//		try
//		{
//			addressFrom = new InternetAddress(
////					"nicloud@nicloud.kr",
////					"mywhoenterprise@tigercompany.kr",
//					"tigrison.com@gmail.com",
//					"TIGRIS",
//					"EUC-KR"
//			);
//			msg.setFrom(addressFrom);
//
//			InternetAddress[] addressTo = new InternetAddress[1];
//			for (int i = 0; i < addressTo.length; i++) {
//				addressTo[i] = new InternetAddress(toEmailAddress, toName, "EUC-KR");
//			}
//			msg.setRecipients(Message.RecipientType.TO, addressTo);
//
//			// Setting the Subject and Content Type
//			msg.setSubject(subject, "UTF-8");
//			MimeBodyPart messageBodyPart = new MimeBodyPart();
//			messageBodyPart.setContent(content, "text/html;charset=UTF-8");
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(messageBodyPart);
//			msg.setContent(multipart);
//
//			Transport.send(msg);
//
//			System.out.println("Send Mail Success!! " + mailHistoryId);
//		}
//		catch (UnsupportedEncodingException | MessagingException e)
//		{
//			System.out.println("Send Mail Fail.. " + mailHistoryId);
//			e.printStackTrace();
//		}
//
//	}
//
//	public Session getJavaMailSession(Properties props, String authId, String authPwd, String smtpAuth) {
//        Authenticator authenticator = null;
//        Boolean bool = new Boolean(smtpAuth);
//        if (bool.booleanValue()) {
//            authenticator = new MailAuthenticator(authId, authPwd);
//            MailAuthenticator emailAuth = (MailAuthenticator) authenticator;
//            props.put("mail.smtp.user", emailAuth.getAccount());
//            props.put("mail.smtp.password", emailAuth.getPassword());
//        }
//        return Session.getInstance(props, authenticator);
//    }
//}
