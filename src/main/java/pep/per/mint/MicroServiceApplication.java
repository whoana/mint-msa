package pep.per.mint;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@ImportResource("classpath:mint-context.xml")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@SpringBootApplication
@RestController
//@MapperScan("pep.per.mint.database.mapper")
@EnableCaching
public class MicroServiceApplication {

	@RequestMapping("/")
    String home() {
        return "Hello World!";
    }


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

	public static void main(String[] args) {


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
