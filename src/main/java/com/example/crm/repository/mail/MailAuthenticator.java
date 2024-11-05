//
//package com.example.crm.repository.mail;
//
//import javax.mail.Authenticator;
//import javax.mail.PasswordAuthentication;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class MailAuthenticator extends Authenticator {
//	private String account;
//	private String password;
//
//	public MailAuthenticator() {
//	}
//
//	public MailAuthenticator(String account, String password) {
//		this.setAccount(account);
//		this.setPassword(password);
//	}
//
//	protected PasswordAuthentication getPasswordAuthentication() {
//		return new PasswordAuthentication( 	this.getAccount(), this.getPassword() );
//	}
//
//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String toString() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("account", this.account);
//		map.put("password", this.password);
//		return map.toString();
//	}
//
//}
