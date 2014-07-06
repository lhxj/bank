package com.bank.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
				bank = new Bank(rs.getInt(1), rs.getString(2), rs.getLong(3),
						rs.getLong(4), rs.getFloat(5));
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
				Bank bank = new Bank(rs.getInt(1), rs.getString(2),
						rs.getLong(3), rs.getLong(4), rs.getFloat(5));
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
				BankTimeDepositRate bank = new BankTimeDepositRate(
						rs.getInt(1), rs.getInt(2), rs.getInt(3),
						rs.getFloat(4));
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

	public BankTimeDepositRate findRate(Integer bankId, Integer year) {
		String sql = "select * from bank_rate where bank_id = 1 and year = "
				+ year;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankTimeDepositRate rate = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rate = new BankTimeDepositRate(rs.getInt(1), rs.getInt(2),
						rs.getInt(3), rs.getFloat(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return rate;
	}

	public boolean updateRRate(Integer bankRateId, Float rate) {
		String sql = "update bank_rate set rate = " + rate + " where id = "
				+ bankRateId;
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

	public boolean timeToLife(Integer userId, Long originAmount,
			Long timeOriginAmount, Long amount, Integer timeId) {
		String sql1 = "update user_account set amount = "
				+ (originAmount + amount) + " where id = " + userId;
		String sql2 = "insert account_log(user_id, deposit_amount, time, remark) values("
				+ userId
				+ ","
				+ amount
				+ ",'"
				+ new Timestamp(System.currentTimeMillis()).toString()
						.substring(0, 19)
				+ "', '帐户原金额："
				+ originAmount
				+ "，定期金额为：" + timeOriginAmount + "转为活期')";
		String sql3 = "delete from account_deposit where id = " + timeId;
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt3 = conn.prepareStatement(sql3);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1
					&& pstmt3.executeUpdate() == 1;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
			DBHelper.closeResources(null, pstmt3, rs);
		}
		return flag;
	}

	public boolean timeOver(Integer userId, Long originAmt, Long timeOriginAmt,
			Long amount, Timestamp time, Integer timeId) {
		String sql1 = "update user_account set amount = "
				+ (originAmt + amount) + " where id = " + userId;
		String sql2 = "insert account_log(user_id, deposit_amount, time, remark) values("
				+ userId
				+ ","
				+ amount
				+ ",'"
				+ new Timestamp(System.currentTimeMillis()).toString()
						.substring(0, 19)
				+ "', '帐户原金额："
				+ originAmt
				+ "，定期金额为："
				+ timeOriginAmt
				+ "到期进行到期的利息计算，并将该定期时间改为结束时间，后续将对该定期超期部分做转活期操作')";
		String sql3 = "update account_deposit set amount_time = '"
				+ time.toString().substring(0, 19) + "' where id = " + timeId;
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt3 = conn.prepareStatement(sql3);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1
					&& pstmt3.executeUpdate() == 1;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBHelper.closeResources(null, pstmt1, rs);
			DBHelper.closeResources(null, pstmt2, rs);
			DBHelper.closeResources(null, pstmt3, rs);
		}
		return flag;
	}
}
