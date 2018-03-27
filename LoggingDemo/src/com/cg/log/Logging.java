package com.cg.log;

import org.apache.log4j.Logger;

public class Logging {

	public static void main(String[] args) {

		Logger logger = Logger.getLogger(Logging.class);
		logger.info("Inside logging");
		
	}

}
