package com.germanium.lms.model.dto;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.context.annotation.Bean;

public class Log {
	public Logger logger;
	FileHandler handler;
	private static final Log instance = new Log();
	public String fileName = "log";

	@Bean
	public static Log getInstance() {
		return instance;
	}

	private Log() {
		if (instance != null) {
			// Prevent Reflection
			throw new IllegalStateException("Cannot instantiate a new singleton instance of log");
		}
		this.createLogFile();
	}

	private void createLogFile() {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			handler = new FileHandler(fileName, true);
			logger = Logger.getLogger("lms");
			logger.setLevel(Level.ALL);
			logger.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		} catch (Exception e) {

		}
	}
}