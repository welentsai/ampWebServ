package com.momo.amp;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmpLogger {
	private static Logger logger = null;
	private static boolean enabled = false;
	
	public AmpLogger() {
	}
	
	public static Logger getLogger() {
		if(!enabled) {
			try {
				LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
				URI logUri = AmpLogger.class.getResource("/momo-amp-log4j.xml").toURI();
				context.setConfigLocation(logUri);
				logger = LoggerFactory.getLogger(AmpLogger.class);
				enabled = true;
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		return logger;
	}
}
