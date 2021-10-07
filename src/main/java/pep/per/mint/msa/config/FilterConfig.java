/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.msa.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pep.per.mint.front.filter.CORSFilter;

/**
 * @author whoana
 * @since 2021. 9. 13.
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer{


	/**
	 *
	 *<filter>
		<filter-name>CORSFilter</filter-name>
		<filter-class>pep.per.mint.front.filter.CORSFilter</filter-class>
		<init-param>
			<param-name>allowed.cors</param-name>
			<param-value>false</param-value>
		</init-param>
		<init-param>
			<param-name>allowed.origins</param-name>
			<param-value>*</param-value>
		</init-param>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CORSFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	 *
	 * FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean registrationCORSFilter() {
		CORSFilter filter = new CORSFilter();
		//filter.
		FilterRegistrationBean bean = new FilterRegistrationBean(filter);
		Map params = new HashMap();
		params.put("allowed.cors", "false");
		params.put("allowed.origins", "*");
		params.put("encoding", "UTF-8");
		bean.setInitParameters(params);
		bean.setUrlPatterns(Arrays.asList("/*"));
		return bean;
	}

}
