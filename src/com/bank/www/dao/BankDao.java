package com.bank.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bank.www.entity.Bank;
import com.bank.www.entity.BankTimeDepositRate;
import com.bank.www.utils.DBHelper;

public class BankDao {

	public Bank loadBank(Integer bankId) {
		String sql = "select * from bank where id = " + bankId;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bank bank = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bank = new Bank(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getLong(4), rs.getFloat(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return bank;
	}

	public List<Bank> list() {
		String sql = "select * from bank";
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Bank> list = new ArrayList<Bank>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bank bank = new Bank(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getLong(4), rs.getFloat(5));
				list.add(bank);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return list;
	}

	public List<BankTimeDepositRate> listRate(Integer bankId) {
		String sql = "select * from bank_rate where bank_id = " + bankId;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BankTimeDepositRate> list = new ArrayList<BankTimeDepositRate>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BankTimeDepositRate bank = new BankTimeDepositRate(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4));
				list.add(bank);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return list;
	}

	public boolean updateRRate(Integer bankRateId, Float rate) {
		String sql = "update bank_rate set rate = " + rate + " where id = " + bankRateId;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			flag = pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return flag;
	}

	public boolean updateCRate(Integer bankId, Float rate) {
		String sql = "update bank set rate = " + rate + " where id = " + bankId;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			flag = pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return flag;
	}

}
