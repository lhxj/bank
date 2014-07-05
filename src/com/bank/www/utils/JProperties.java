package com.bank.www.utils;

import java.io.IOException;
import java.util.Properties;

public class JProperties {

	private static Properties p = new Properties();
	// 用静态代码块
	static {
		try {
			p.load(JProperties.class.getResourceAsStream("jdbc.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDriver() {
		return p.getProperty("driver");
	}

	public static String getUrl() {
		return p.getProperty("url");
	}

	public static String getUser() {
		return p.getProperty("user");
	}

	public static String getPwd() {
		return p.getProperty("password");
	}

}
