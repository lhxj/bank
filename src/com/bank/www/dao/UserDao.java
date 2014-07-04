package com.bank.www.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bank.www.entity.UserAccounts;
import com.bank.www.utils.DBHelper;

public class UserDao {

	public DBHelper db;
	private String sql;
	private ResultSet ret;
	private Statement st;

	public UserAccounts loadUser(Integer userId) {
		sql = "select * from user_account where id = " + userId;
		db = new DBHelper(sql);
		UserAccounts userAccounts = null;
		try {
			ret = db.pst.executeQuery();
			while (ret.next()) {
				userAccounts = new UserAccounts(ret.getInt(1), ret.getString(2), ret.getString(3), ret.getInt(4),
						ret.getLong(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return userAccounts;
	}

	public UserAccounts findUserByNO(String no) {
		sql = "select * from user_account u where u.no = " + no;
		db = new DBHelper(sql);
		UserAccounts userAccounts = null;
		try {
			ret = db.pst.executeQuery();
			while (ret.next()) {
				userAccounts = new UserAccounts(ret.getInt(1), ret.getString(2), ret.getString(3), ret.getInt(4),
						ret.getLong(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return userAccounts;
	}

	public boolean create(String userName, String no) {
		sql = "insert into user_account value(" + userName + "," + no + ", 1, 0)";
		db = new DBHelper(sql);
		boolean flag = false;
		try {
			st = db.conn.createStatement();
			flag = st.executeUpdate(sql) == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return flag;
	}

	public boolean updateState(Integer userId, Integer state) {
		sql = "update user_account set state = " + state + " where id = " + userId;
		db = new DBHelper(sql);
		boolean flag = false;
		try {
			st = db.conn.createStatement();
			flag = st.executeUpdate(sql) == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return flag;
	}

	public boolean updateAmount(Integer userId, Long amount) {
		sql = "update user_account set amount = " + amount + " where id = " + userId;
		db = new DBHelper(sql);
		boolean flag = false;
		try {
			st = db.conn.createStatement();
			flag = st.executeUpdate(sql) == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return flag;
	}

	public boolean exchange(Integer outId, Long outamount, Integer inId, Long inamount) {
		boolean flag = false;
		try {
			db.conn.setAutoCommit(false);
			flag = updateAmount(outId, outamount) && updateAmount(inId, inamount);
			db.conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return flag;
	}

	private void close() {
		try {
			if (ret != null) {
				ret.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
