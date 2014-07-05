package com.bank.www.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {

	public static Connection conn;

	static {
		try {
			Class.forName(JProperties.getDriver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取数据库的连接
	 * 
	 * @return conn
	 */
	public static Connection getConnection() {
		if (null == conn) {
			try {
				conn = DriverManager.getConnection(JProperties.getUrl(), JProperties.getUser(), JProperties.getPwd());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return conn;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param pstmt
	 * @param rs
	 */
	public static void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (null != pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
