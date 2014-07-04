package com.bank.www.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class DBHelper {

	public Connection conn;
	public PreparedStatement pst = null;
	private String driver;
	private String url;
	private String username;
	private String password;

	public DBHelper() {
		Properties p = new JProperties().getPro();
		this.driver = p.getProperty("driver");
		this.url = p.getProperty("url");
		this.username = p.getProperty("user");
		this.password = p.getProperty("password");
	}

	public DBHelper(String sql) {
		this();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
			if (this.pst != null) {
				this.pst.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
