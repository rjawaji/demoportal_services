package com.pgba.config;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.springframework.util.StringUtils;

@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(4)
public class CustomConfigurationFactory extends ConfigurationFactory {
	
	 private final boolean isActive;
	 
	 public CustomConfigurationFactory() {
	       isActive = true;
	 }
	 
	 

   @Override
    public Configuration getConfiguration(final ConfigurationSource source) {
        return getConfiguration(source.toString(), null);
    }
    @Override
    public Configuration getConfiguration(final String name, final URI configLocation) {
    	 ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }
    
    @Override
    protected boolean isActive() {
        return isActive;
    }
  
    
    private Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
    	
    	/*String environment = System.getProperty("spring.profiles.active");
    	String logFilePath = System.getProperty("RRBPORTAL.log4j.path");
    	String loggerLevel = System.getProperty("RRBPORTAL.log4j.logger.level");
    	String rootLevel = System.getProperty("RRBPORTAL.log4j.root.level");*/
    	String environment = "DEV";
    	String logFilePath = "D:/applogs/";
    	String loggerLevel = "DEBUG";
    	String rootLevel = "DEBUG";
    	
    	System.out.println("******************LOG4j Custom Configuration Details******************");
    	System.out.println("\tEnvironment:- " +environment); 
    	System.out.println("\tLog File Path:- " +logFilePath);
    	System.out.println("\tLogger Level:- " + loggerLevel);
    	System.out.println("\tRoot Logger Level:- " + rootLevel);
    	System.out.println("**********************************************************************");
    	
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.DEBUG);
        builder.setMonitorInterval("30");       
//        builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
//            addAttribute("level", Level.DEBUG));
        
        // Create a console appender
        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").
            addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT).addAttribute("ignoreExceptions", false);
        appenderBuilder.add(builder.newLayout("PatternLayout").
            addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"));
        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
            Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
        builder.add(appenderBuilder);
        
        // Layout Builder
        LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d [%t] %-5level: %msg%n");
        ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                .addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));
                
        appenderBuilder = builder.newAppender("rolling", "RollingFile")
            .addAttribute("fileName", getLogPath(environment, logFilePath)+ "demoportal_services.log")
            .addAttribute("filePattern", getLogPath(environment, logFilePath)+ "demoportal_services-%d{MM-dd-yyyy}.log")
            .add(layoutBuilder)
            .addComponent(triggeringPolicy);
		builder.add(appenderBuilder);
        				
		builder.add(builder.newLogger("com.pgba", getLogLevel(environment, loggerLevel)).
          add(builder.newAppenderRef("rolling")).
          addAttribute("additivity", false));
		builder.add(builder.newRootLogger(getLogLevel(environment, rootLevel)).add(builder.newAppenderRef("rolling")));
		System.out.println("******************LOG4j Custom Configuration Build Success ******************");
        return builder.build();
    }

    
    
    private String getLogPath(String environment, String logFilePath) {
    	System.out.println("******************LOG4j GetLogPath ******************");
    	if(!StringUtils.isEmpty(environment) && !StringUtils.isEmpty(logFilePath)) {
    		return logFilePath;
        } else if(!StringUtils.isEmpty(environment) && StringUtils.isEmpty(logFilePath)) {
        	if(!StringUtils.isEmpty(environment) && environment.equalsIgnoreCase("PROD")) { // Default PROD Log Path
        		logFilePath = "/applogs/RRBPortal/";
        	} else if(!StringUtils.isEmpty(environment) && environment.equalsIgnoreCase("TEST")) { // Default TEST Log Path
        		logFilePath = "/applogs/RRBPortal/";
        	} else { // Default DEV Log Path
        		logFilePath = "D:/applogs/";
        	}
        } else {
        	logFilePath = "D:/applogs/";
        }
    	
    	return logFilePath;
    }
    
    private Level getLogLevel(String environment, String logLevel) {
    	System.out.println("******************LOG4j GetLog Level ******************");
    	if(!StringUtils.isEmpty(environment) && environment.equalsIgnoreCase("PROD")) {
        	if(!StringUtils.isEmpty(logLevel)) {
        		if(logLevel.equalsIgnoreCase("FATAL")) {
        			return Level.FATAL;
        		} else if(logLevel.equalsIgnoreCase("ERROR")) {
        			return Level.ERROR;
        		} else if(logLevel.equalsIgnoreCase("WARN")) {
        			return Level.WARN;
        		} else if(logLevel.equalsIgnoreCase("INFO")) {
        			return Level.INFO;
        		} else if(logLevel.equalsIgnoreCase("DEBUG")) {
        			return Level.DEBUG;
        		} 
        	} else {
        		return Level.ERROR;
        	}
        } else if(!StringUtils.isEmpty(environment) && environment.equalsIgnoreCase("TEST")) {
        	if(!StringUtils.isEmpty(logLevel)) {
        		if(logLevel.equalsIgnoreCase("FATAL")) {
        			return Level.FATAL;
        		} else if(logLevel.equalsIgnoreCase("ERROR")) {
        			return Level.ERROR;
        		} else if(logLevel.equalsIgnoreCase("WARN")) {
        			return Level.WARN;
        		} else if(logLevel.equalsIgnoreCase("INFO")) {
        			return Level.INFO;
        		} else if(logLevel.equalsIgnoreCase("DEBUG")) {
        			return Level.DEBUG;
        		} 
        	} else {
        		return Level.INFO;
        	}
        }  else {
        	return Level.DEBUG;
        }
		return Level.DEBUG;
    }    
    
}

