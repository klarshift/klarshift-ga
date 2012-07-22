package com.klarshift.artificial.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Logging {
	public static void setup(){		
		try {
			//PropertyConfigurator.configure("log4j.properties");
			
			Logger.getRootLogger().removeAllAppenders();
			BasicConfigurator.configure();

			/*Logger logger = Logger.getRootLogger();
			
		
			String pattern = "%p | %d{ISO8601} | %C | %m %n";
			
			
			ConsoleAppender appender = new ConsoleAppender(new PatternLayout(pattern));
			//BasicConfigurator.configure(appender);
			
			logger.removeAllAppenders();
			logger.addAppender(appender);
			
			/*FileAppender fileAppender = new FileAppender(layout,
					"logs/MeineLogDatei.log", false);
			
			logger.addAppender(fileAppender);*/
			// ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
			//logger.setLevel(Level.WARN);
			//logger.info("SETUP");*/
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static void setLevel(Level level) {
		Logger.getRootLogger().setLevel(level);
	}	
}
