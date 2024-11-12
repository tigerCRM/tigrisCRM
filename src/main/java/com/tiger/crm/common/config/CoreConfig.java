package com.tiger.crm.common.config;

import com.tiger.crm.common.context.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.support.ServletContextAttributeExporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAsync
@EnableScheduling
// @PropertySource(name="config", value="classpath:tigris-${spring.profiles.active}.properties")
public class CoreConfig {

	@Autowired public ConfigProperties config;


	@Bean
    public ServletContextAttributeExporter servletContextAttributeExporter() throws IOException {

		ServletContextAttributeExporter exporter = new ServletContextAttributeExporter();

		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("config", config);

		exporter.setAttributes(attributes);

        return exporter;
    }

}
