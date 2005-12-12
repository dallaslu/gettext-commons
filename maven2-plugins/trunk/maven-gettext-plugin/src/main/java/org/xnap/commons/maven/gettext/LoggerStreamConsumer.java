package org.xnap.commons.maven.gettext;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.cli.StreamConsumer;

public class LoggerStreamConsumer implements StreamConsumer {

	public static final int DEBUG = 0;
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int ERROR = 3;
	
	private Log logger;
	private int loglevel;
	
	public LoggerStreamConsumer(Log logger, int loglevel) {
		this.logger = logger;
		this.loglevel = loglevel;
	}
	
	public void consumeLine(String line) {
		if (loglevel == DEBUG) {
			logger.debug(line);
		} else if (loglevel == INFO) {
			logger.info(line);
		} else if (loglevel == WARN) {
			logger.warn(line);
		} else if (loglevel == ERROR) {
			logger.error(line);
		}
	}

}
