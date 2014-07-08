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
import com.bank.www.entity.StatisticsBean;
import com.bank.www.utils.DBHelper;

public class BankDao {

	/**
	 * 查询银行信息
	 * 
	 * @param bankId
	 *            银行 id
	 * @return
	 */
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

	/**
	 * 查询银行信息
	 * 
	 * @return
	 */
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

	/**
	 * 查询银行定期存款利率
	 * 
	 * @param bankId
	 * @return
	 */
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

	/**
	 * 根据年限查询银行该年限的定期利率
	 * 
	 * @param bankId
	 * @param year
	 * @return
	 */
	public BankTimeDepositRate findRate(Integer bankId, Integer year) {
		String sql = "select * from bank_rate where bank_id = 1 and year = " + year;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankTimeDepositRate rate = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rate = new BankTimeDepositRate(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return rate;
	}

	/**
	 * 修改银行定期利率
	 * 
	 * @param bankRateId
	 *            定期ID
	 * @param rate
	 *            利率
	 * @return
	 */
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

	/**
	 * 修改银行活期利率
	 * 
	 * @param bankId
	 *            银行ID
	 * @param rate
	 *            活期利率
	 * @return
	 */
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

	/**
	 * 定期存款不到期转为活期存款
	 * 
	 * @param accountId
	 *            账户ID
	 * @param originAmount
	 *            账户原始金额
	 * @param timeOriginAmount
	 *            定期存款原始金额
	 * @param amount
	 *            转入活期账户金额
	 * @param timeId
	 *            定期ID
	 * @param timeOriAmt
	 *            定期存款原始金额
	 * @return
	 */
	public boolean timeToLife(Integer accountId, Long originAmount, Long timeOriginAmount, Long amount, Integer timeId,
			Long timeOriAmt) {
		String sql1 = "update user_account set amount = " + (originAmount + amount) + " where id = " + accountId;
		String sql2 = "insert account_log(account_id, deposit_amount, time, type, remark) values(" + accountId + "," + amount
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 2, '帐户原金额："
				+ originAmount + "，定期金额为：" + timeOriginAmount + "转为活期')";
		String sql3 = "delete from account_deposit where id = " + timeId;
		String sql4 = "insert account_log(account_id, draw_amount, time, type, remark) values(" + accountId + "," + timeOriAmt
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 1, '帐户原金额："
				+ timeOriAmt + "，定期转为活期取消了该定期的存款')";
		boolean flag = false;
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt3 = conn.prepareStatement(sql3);
			pstmt4 = conn.prepareStatement(sql4);
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1 && pstmt3.executeUpdate() == 1
					&& pstmt4.executeUpdate() == 1;
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
			DBHelper.closeResources(null, pstmt4, rs);
		}
		return flag;
	}

	/**
	 * 定期到期后转为活期存款操作
	 * 
	 * @param accountId
	 *            账户ID
	 * @param originAmt
	 *            账户活期原始金额
	 * @param timeOriginAmt
	 *            定期原始金额
	 * @param amount
	 *            定期到期后可转为活期存款的金额
	 * @param time
	 *            定期到期时间
	 * @param timeId
	 *            定期ID
	 * @return
	 */
	public boolean timeOver(Integer accountId, Long originAmt, Long timeOriginAmt, Long amount, Timestamp time, Integer timeId) {
		String sql1 = "update user_account set amount = " + (originAmt + amount) + " where id = " + accountId;
		String sql2 = "insert account_log(account_id, deposit_amount, time, type, remark) values(" + accountId + "," + amount
				+ ",'" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 19) + "', 2, '帐户原金额：" + originAmt
				+ "，定期金额为：" + timeOriginAmt + "到期进行到期的利息计算，并将该定期时间改为结束时间，后续将对该定期超期部分做转活期操作')";
		String sql3 = "update account_deposit set amount_time = '" + time.toString().substring(0, 19) + "' where id = "
				+ timeId;
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
			flag = pstmt1.executeUpdate() == 1 && pstmt2.executeUpdate() == 1 && pstmt3.executeUpdate() == 1;
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

	/**
	 * 按时间进行统计定期和活期存取款详细数据
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public List<StatisticsBean> statistics(Timestamp begin, Timestamp end) {
		List<StatisticsBean> list = new ArrayList<StatisticsBean>();
		String sql = "select u.user_name, u.no, al.draw_amount, al.deposit_amount, al.type, al.time ";
		sql += "from account_log al left join user_account ua on ua.id = al.account_id left join user u on ua.user_id = u.id where ";
		sql += "al.time >= '" + begin.toString().substring(0, 19) + "' and al.time <= '" + end.toString().substring(0, 19);
		sql += "' order by al.time";
		Connection conn = DBHelper.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisticsBean sb = new StatisticsBean(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getLong(4),
						rs.getInt(5), rs.getTimestamp(6));
				list.add(sb);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.closeResources(null, pstmt, rs);
		}
		return list;
	}
}
