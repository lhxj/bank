package com.bank.www.utils;

import java.io.InputStream;
import java.util.Properties;

public class JProperties {

	private Properties properties = null;
	private static String name = "jdbc.properties";

	public Properties getPro() {
		InputStream in;
		try {
			in = JProperties.class.getResourceAsStream(name);
			if (in != null) {
				properties = new Properties();
				properties.load(in);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

}
