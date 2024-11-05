//package com.example.crm.repository.mail;
//
//import com.tigrison.core.context.ConfigProperties;
//import com.tigrison.core.util.BeanUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.io.UnsupportedEncodingException;
//import java.util.Properties;
//
//public class MailSender {
//
//	private Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//	public void sendMail(String subject, String content, String toEmailAddress, String toName, String mailHistoryId)
//	{
//		ConfigProperties configProperties = (ConfigProperties) BeanUtils.findBean("configProperties");
//
//		LOGGER.info("Send Mail Start.. " + mailHistoryId);
//
//		Properties props = new Properties();
//        props.put("mail.debug", 				configProperties.getMailDebug());
//        props.put("mail.transport.protocol", 	configProperties.getMailTransportProtocol());
//        props.put("mail.smtp.host", 			configProperties.getMailSmtpHost());
//        props.put("mail.smtp.port", 			configProperties.getMailSmtpPort());
//        props.put("mail.smtp.auth", 			configProperties.getMailSmtpAuth());
//        props.put("mail.smtp.starttls.enable", 	configProperties.getMailSmtpStarttlsEnable());
//
//        boolean smtpSarttls = true;
//        boolean smtpSocketFactorUse = true;
//
//        if (smtpSarttls) {
//            if (smtpSocketFactorUse) {
//                props.put("mail.smtp.socketFactory.port", 		configProperties.getMailSmtpSocketFactoryPort());
//                props.put("mail.smtp.socketFactory.class", 		configProperties.getMailSmtpSocketFactoryClass());
//                props.put("mail.smtp.socketFactory.fallback", 	configProperties.getMailSmtpSocketFactoryFallback());
//            }
//        }
//
//        Session javaMailSession = getJavaMailSession(
//        		props,
//        		configProperties.getMailSmtpAuthDefaultAccountId(),
//        		configProperties.getMailSmtpAuthDefaultAccountPwd(),
//        		configProperties.getMailSmtpAuth()
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
//					configProperties.getMailSmtpAuthDefaultAccountEmail(),
//					configProperties.getMailSmtpAuthDefaultAccountName(),
//					configProperties.getMailSmtpEncoding()
//			);
//			msg.setFrom(addressFrom);
//
//			InternetAddress[] addressTo = new InternetAddress[1];
//			for (int i = 0; i < addressTo.length; i++) {
//				addressTo[i] = new InternetAddress(toEmailAddress, toName, configProperties.getMailSmtpEncoding());
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
//			LOGGER.info("Send Mail Success!! " + mailHistoryId);
//		}
//		catch (UnsupportedEncodingException | MessagingException e)
//		{
//			LOGGER.info("Send Mail Fail.. " + mailHistoryId);
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
//
//}
