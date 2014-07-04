package com.bank.www.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bank.www.entity.Bank;
import com.bank.www.entity.BankTimeDepositRate;
import com.bank.www.utils.DBHelper;

public class BankDao {

	private DBHelper db;
	private String sql;
	private ResultSet ret;
	private Statement st;

	public Bank loadBank(Integer bankId) {
		sql = "select * from bank where id = " + bankId;
		db = new DBHelper(sql);
		Bank bank = null;
		try {
			ret = db.pst.executeQuery();
			while (ret.next()) {
				bank = new Bank(ret.getInt(1), ret.getString(2), ret.getLong(3), ret.getLong(4), ret.getFloat(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return bank;
	}

	public List<Bank> list() {
		sql = "select * from bank";
		db = new DBHelper(sql);
		List<Bank> list = new ArrayList<Bank>();
		try {
			ret = db.pst.executeQuery();
			while (ret.next()) {
				Bank bank = new Bank(ret.getInt(1), ret.getString(2), ret.getLong(3), ret.getLong(4), ret.getFloat(5));
				list.add(bank);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return list;
	}

	public List<BankTimeDepositRate> listRate(Integer bankId) {
		sql = "select * from bank_rate where bank_id = " + bankId;
		db = new DBHelper(sql);
		List<BankTimeDepositRate> list = new ArrayList<BankTimeDepositRate>();
		try {
			ret = db.pst.executeQuery();
			while (ret.next()) {
				BankTimeDepositRate bank = new BankTimeDepositRate(ret.getInt(1), ret.getInt(2), ret.getInt(3),
						ret.getFloat(4));
				list.add(bank);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.close();
			close();
		}
		return list;
	}

	public boolean updateRate(Integer bankRateId, Float rate) {
		sql = "update bank_rate set rate = " + rate + " where id = " + bankRateId;
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
