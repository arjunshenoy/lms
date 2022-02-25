package com.germanium.lms.model.dto;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.context.annotation.Bean;

public class Log {
	private Logger logger;
	FileHandler handler;
	private static Log instance = null;
	public static String fileName = "log";

	@Bean
	public static Log getInstance() {
		return instance;
	}

	private Log() {
		if (instance == null) {
			instance = new Log();
		}
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
			setLogger(Logger.getLogger("lms"));
			getLogger().setLevel(Level.ALL);
			getLogger().addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		} catch (IOException e) {
			logger.log(Level.SEVERE, fileName);
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}