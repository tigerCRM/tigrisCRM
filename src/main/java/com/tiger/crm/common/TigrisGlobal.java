package com.tiger.crm.common;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.context.Profile;
import com.tiger.crm.common.crypto.MD5Utils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.UUID;

/**
 * <code>TigrisGlobal.java</code>
 *
 * @author Jaehwan Lee
 * @since 2016. 3. 15.
 * @version 1.0
 */
@Slf4j
@Component
public class TigrisGlobal implements ServletContextAware, InitializingBean {

	@Autowired private ConfigProperties config;
	@Autowired private DataSource dataSource;

	// com.tigrison -> MD5
	public static final String AES_SECRET_KEY = "330ed346c97712232ba447f49b8846eb";
	public static final String SEED_SECRET_KEY = "gc@cenworksMail!";

	public static String SERVER_ID;

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		log.info("Database Product Name : {}", dataSource.getConnection().getMetaData().getDatabaseProductName());

		String pid = ManagementFactory.getRuntimeMXBean().getName();
		String appRootKey = "tigris";

		if(servletContext != null) {
			appRootKey = servletContext.getInitParameter("webAppRootKey");
		}

		SERVER_ID = String.format("%s:%s", pid, appRootKey);

		log.info("SERVER ID : {}", SERVER_ID);
	}

	/**
	 * 현재 운영중인 서버의 ID를 반환한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getServerId() {
		return SERVER_ID;
	}

	public String getWebAppRootKeyByAPI() throws Exception {
		String appRootKey = "";
		if(servletContext != null) {
			appRootKey = servletContext.getInitParameter("webAppRootKey");
			if(appRootKey.equals("tigris-api")) {
				appRootKey += ":";
			} else {
				appRootKey = "";
			}
		}
		return appRootKey;
	}

	/**
	 * <p>
	 * MQ 서버의 Exchange name 을 반환한다.<br>
	 * Profile이 DEV/LOCAL 일 경우 hostName 을 tail 에 더해 Exchange name을 생성한다.<br><br>
	 * ex)
	 * 	<ul>
	 * 		<li>tigris-test-exchange</li>
	 * 		<li>tigris-cloud-exchange</li>
	 * 		<li>tigris-dev-exchange-hostName</li>
	 * </p>
	 * @return
	 * @throws Exception
	 */
	public String getMQExchangeName() throws Exception {

		String profile = config.getProfile();
		String exchangeName = "tigris-" + profile + "-exchange";

		if(Profile.DEV.value().equals(profile) || Profile.LOCAL.value().equals(profile)) {
			String hostName = InetAddress.getLocalHost().getHostName();
			exchangeName += "-" + hostName;
		}

		return exchangeName;
	}

	/**
	 * <p>
	 * MQ 서버의 Queue name 을 반환한다.<br>
	 * Profile이 DEV일 경우 hostName 을 tail 에 더해 Queues name을 생성한다.<br><br>
	 * ex)
	 * 	<ul>
	 * 		<li>tigris-test-#queueName</li>
	 * 		<li>tigris-cloud-#queueName</li>
	 * 		<li>tigris-dev-#queueName-hostName</li>
	 * </p>
	 * @return
	 * @throws Exception
	 */
	public String getMQQueueName(String queueName) throws Exception {

		String profile = config.getProfile();
		String customQueueName = "tigris-" + profile + "-" + queueName;

		if(Profile.DEV.value().equals(profile) || Profile.LOCAL.value().equals(profile)) {
			String hostName = InetAddress.getLocalHost().getHostName();
			customQueueName += "-" + hostName;
		}

		return customQueueName;
	}



	/**
	 * <p>nanosecond + UUID</p>
	 * @return
	 */
	public static String generateID() {
		return MD5Utils.md5toHex(System.nanoTime() + "-" + UUID.randomUUID().toString());
	}

	/**
	 * User-Agent 값을 분석해 모바일 기기인지 여부를 판단한다.
	 * @param userAgent
	 * @return
	 */
	public static boolean isMobileApp(String userAgent) {
		if (userAgent == null)
			return false;

		if (userAgent.indexOf("TIGRIS/Android") != -1)
			return true;

		if (userAgent.indexOf("TIGRIS/iOS") != -1) {
			return true;
		}

		return false;
	}

	/**
	 * 클라이언트의 접속 IP를 구한다.
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("Proxy-Client-IP");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getRemoteAddr();
		}

		return ip;
	}




	public static void main(String[] args)
	{
		System.out.println("generateID : " + TigrisGlobal.generateID());
	}
}
