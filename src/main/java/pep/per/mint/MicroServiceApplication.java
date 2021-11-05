package pep.per.mint;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.filter.CORSFilter;

@ImportResource("classpath:mint-context.xml")
@SpringBootApplication(
		scanBasePackages = "pep.per.mint",
		exclude={DataSourceAutoConfiguration.class}
)
//@SpringBootApplication
//@RestController
//@MapperScan("pep.per.mint.database.mapper")
@EnableCaching
public class MicroServiceApplication {

//	@RequestMapping("/")
//    String home() {
//        return "Hello World!";
//    }

	
	 

    //<beans:bean id="restTemplate" class="org.springframework.web.client.RestTemplate"/>
//	@Bean("restTemplate")
//	public RestTemplate getRestTemplate1() {
//		return new RestTemplate();
//	}

	//<beans:bean id="eucKrRestTemplate" class="org.springframework.web.client.RestTemplate"/>
//	@Bean("eucKrRestTemplate")
//	public RestTemplate getRestTemplate2() {
//		return new RestTemplate();
//	}


//	<beans:bean id="messageSource"
//	        class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
//	        <beans:property name="basename" value="classpath:messages/messages" />
//	        <beans:property name="defaultEncoding" value="UTF-8" />
//	    </beans:bean>
//    @Bean("messageSource")
//    public MessageSource getMessageSource() {
//    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//    	messageSource.setBasename("classpath:messages/messages");
//    	messageSource.setDefaultEncoding("UTF-8");
//
//    	return messageSource;
//    }


	/*
	@Bean("cacheManager")
	public CacheManager getCacheManager() {
		SimpleCacheManager scm = new SimpleCacheManager();
		ConcurrentMapCacheFactoryBean cmcfb = new ConcurrentMapCacheFactoryBean();
		cmcfb.setName("authorities");
		List caches = new ArrayList();
		caches.add(cmcfb.getObject());

		scm.setCaches(caches);

		return scm;
	}
	*/

	static Logger logger = LoggerFactory.getLogger(MicroServiceApplication.class);
	
	public static void main(String[] args) {
		//System.getProperties().list(System.out);
		logger.info("user.dir:" + System.getProperty("user.dir"));
		logger.info("string boot entry point: main(String[] args)");
		
		String home = System.getProperty("user.dir");
		File runscript = new File(home, "run");
		BufferedInputStream bis = null;
		if(runscript.exists()) {
			try { 
				 bis = new BufferedInputStream(new FileInputStream(runscript));
				
				int ch = 0;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				do {
					ch = bis.read();
					baos.write(ch);
				}while(ch != -1);
				
				String script = baos.toString();
				logger.info("run script:\n"+ baos.toString());
				if(!script.contains("JAVA_HOME")) {
					logger.info("This script has no java home.");
				}
				
				if(!script.contains("MINT_HOME")) {
					logger.info("This script has no mint home.");
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(bis != null)
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		
		for(String arg : args) {
			logger.info(arg);
		}
		
		//System.getenv().put("system.file.upload.path", "/Users/whoana/DEV/workspace-refactoring/mint-msa/upload");

		SpringApplication sa = new SpringApplication(MicroServiceApplication.class);

		ApplicationContextInitializer<ConfigurableApplicationContext> aci = new ApplicationContextInitializer<ConfigurableApplicationContext>() {

			@Override
			public void initialize(ConfigurableApplicationContext applicationContext) {

				System.out.println("MINT SPRING BOOT >>>>>>>>>>>>>");

			}
		};

		sa.addInitializers(aci);
		//sa.addListeners(null);
		sa.run(args);
		//SpringApplication.run(MicroServiceApplication.class, args);
	}

}
