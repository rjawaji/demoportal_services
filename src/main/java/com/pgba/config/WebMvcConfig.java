package com.pgba.config;





import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.pgba.*"}, excludeFilters={ @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class) })
//@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.add(createXmlHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
 
        super.configureMessageConverters(converters);
    }
	
	 @Override
	 public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addViewController("/").setViewName("index");
	 }
	 
/*	@Bean(name = "envProps")
	public static PropertyPlaceholderConfigurer properties(Environment env) {
		
		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		YamlPropertiesFactoryBean yamlProps = new YamlPropertiesFactoryBean();
		yamlProps.setResources(new ClassPathResource("application.yml"));
		yamlProps.setEnvironment(env);
		Properties props = yamlProps.createProperties();
		propertyPlaceholderConfigurer.setProperties(props);
		return propertyPlaceholderConfigurer;
	}*/
	
	
	@Profile(value = {"DEV", "TEST"})
	public void setDebugLevel() {
		changeLoglevel(Level.DEBUG);
	}
	
	@Profile(value = {"PROD"})
	public void setInfoLevel() {
		changeLoglevel(Level.INFO);
	}
	
	public void changeLoglevel(Level level) {
		LoggerContext ctx = (LoggerContext) LogManager.getContext();
		org.apache.logging.log4j.core.config.Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(level);
		ctx.updateLoggers();
	}

}